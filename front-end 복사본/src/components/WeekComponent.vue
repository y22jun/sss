<script setup>
import { useRouter, useRoute} from "vue-router";
import { ref, defineProps, onMounted } from "vue";
import { useSessionStore } from "@/stores/session"
import { api } from "@/utils/axios"

//pinia
const sessionStore = useSessionStore();
const props = defineProps({
  weeklyExpenderProps: Boolean,
})
const route = useRoute();
const router = useRouter();
const weekList = ref([]);
const emit = defineEmits(["change-component"]);

function moveDailyView(id) {
  router.push({ name: "daily-view", params: { id: id } });
  emit("change-component", id);
}

/*
gruop_week_id
group_week_title
*/
const getWeekProbList = async () => {
  const data = {
    user_token: sessionStore.cookies.get("token"),
    user_email: sessionStore.cookies.get("email"),
    group_id : route.params.id
  }
  const result = await api.group.getWeekProblem(data);

  for(const idx in result){
    weekList.value.push(result[idx]);
  }
}
onMounted(() => {
  getWeekProbList();
})
</script>

<template>
  <!-- 목차 -->
    <div class="problem-items" :style="{'max-height': weeklyExpenderProps ? '20vh' : '45vh' }">
      <!-- id번호로 이동 -->
      <!-- for문 추가 예정 DB에 따른 -->
      <div v-for="(weekListItem,idx) in weekList" :key="idx">
        <div class="problem-item" @click="moveDailyView(1)">
          <p class="problem-source">{{idx}}</p>
          <div class="vr"></div>
          <p class="problem-name">{{weekListItem.group_week_title}}</p>
          <div class="vr"></div>
          <div class="problem-solve-gage progress">
            <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="75"
              aria-valuemin="0" aria-valuemax="100" style="width: 75%"></div>
          </div>
        </div>
      </div>
    </div>

</template>

<style scoped>

.problem-items{
  overflow-y: auto;
}
/* 수직 스크롤바 */
.problem-items::-webkit-scrollbar {
  width: 10px; /* 스크롤바의 너비 */
}

.problem-items::-webkit-scrollbar-track {
  background: #f1f1f1; /* 스크롤바 트랙 배경색 */
}

.problem-items::-webkit-scrollbar-thumb {
  background: #888; /* 스크롤바 썸 배경색 */
  border-radius: 5px; /* 스크롤바 썸 모서리 반경 */
}

.problem-items::-webkit-scrollbar-thumb:hover {
  background: #555; /* 스크롤바 썸 호버 배경색 */
}

.problem-item {
  display: flex;
  flex-direction: row;
  position: relative;
  margin-top: 0.3%;
  padding: 10px;
  /* border-radius: 5px; */
  /* border-bottom: 3px solid rgba(0, 0, 0, 0.1); */
  /* border: 1px solid rgba(0, 0, 0, 0.1); */
  margin-top: 0.3%;
  margin-bottom: 0.3%;
  text-align: center;
  width: 100%;
  height: 40px;
}

.problem-item:hover {
  background-color: rgba(0, 0, 0, 0.1);
}

.problem-source {
  flex: 0.05;
}

.problem-name {
  flex: 0.9;
  text-align: left;
  margin-left: 1%;
}

.problem-solve-gage {
  margin-left: 10px;
  flex: 0.1;
}

.btn-group-vertical {
  max-height: 100vh;
  width: 100%;
}

.btn {
  width: 100%;
  text-align: left;
}
</style>
