<script setup>
import WeekView from "@/components/WeekComponent.vue";
import DailyView from "@/components/DailyComponent.vue";
import { onMounted, watch, ref} from "vue";
import { useSessionStore } from "@/stores/session";
import { api } from "@/utils/axios"
import { useRoute, useRouter } from "vue-router"

const sessionStore = useSessionStore();
const route = useRoute();
const router = useRouter();
const dailyExpender = ref(false);
const weeklyExpender = ref(false);
const isDailyList = ref(false);
const exnpending = ref(false);
const GroupData = ref();
const role = ref(false);

const toggleExpender = () =>{
  if(!exnpending.value){
    dailyExpender.value = !dailyExpender.value;
    weeklyExpender.value = !weeklyExpender.value;
  }

  exnpending.value = true;
  setTimeout(() => {
    isDailyList.value = dailyExpender.value;
    exnpending.value = false;
  }, 500);
}


const routeLink = (name ,Id) => {
  router.push({'name' : name, 'params' : {'id' : Id}})
}

const getMyGroup = async () => {
  // 해당 그룹 정보 받아오기
  const data = {
    user_token: sessionStore.cookies.get("token"),
    user_email: sessionStore.cookies.get("email"),
    group_id : route.params.id
  }
  const result = await api.group.getById(data);
  const result_role = await api.group.getRole(data);
  
  role.value = result_role.group_role;
  GroupData.value = result;
}

onMounted(async () => {
  await sessionStore.session();
  await getMyGroup();
})

watch(() => route.params.id, () => {
  getMyGroup();
});

/*
GroupData
ㄴ group_id
ㄴ group_name
ㄴ group_info
ㄴ gorup_rule
ㄴ group_min_people
ㄴ group_max_people
ㄴ group_private
*/

</script>
<template>
  <div id="header">
    <h1>{{ GroupData &&  GroupData.group_name}}</h1>
    <button class="btn custom-btn" v-if="role" @click="routeLink('groupAddProb', route.params.id)">추가</button>
  </div>

  <div class="row">
    <div class="notice-group col-8 p-3 m-1">
      <h1>그룹 공지</h1>
      <p>{{ GroupData && GroupData.group_info }}</p>
    </div>
      <div class="rank-group col p-3 m-1">
        <div class="container row">
        <font-awesome-icon class="icon col-1" icon="ranking-star"></font-awesome-icon>
          <h3 class="col">개인별 달성도</h3>
          <br>
        </div>
        <div class="user-progress-bar">
          <div class="progress m-1" role="progressbar" aria-label="Example with label" aria-valuenow="25"
            aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar" style="width: 76%">76%</div>
          </div>
          <div class="progress m-1" role="progressbar" aria-label="Example with label" aria-valuenow="25"
            aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar" style="width: 25%">25%</div>
          </div>
          <div class="progress m-1" role="progressbar" aria-label="Example with label" aria-valuenow="25"
            aria-valuemin="0" aria-valuemax="100">
            <div class="progress-bar" style="width: 25%">25%</div>
          </div>
        </div>
      </div>
  </div>

  <div class="row">
    <div class="col-8" style="padding-right:2vh">
      <div class="row">
        <div class="card-form p-3 m-1 row" @click="toggleExpender" :class="{ 'shifted': dailyExpender, 'group-daily-collapsed': !dailyExpender, }">
          <div class="headline">
            <h3 class="pointer" @click="routeLink('daily-view', 1)">이번주 문제</h3>
            <font-awesome-icon class="icon col-1" :icon="!dailyExpender ? 'chevron-down' : 'chevron-up'"></font-awesome-icon>
          </div>
          <DailyView v-if="!exnpending && isDailyList"/>
        </div>
        <div class="card-form p-3 m-1 row " :class="{ 'shifted': weeklyExpender, 'group-weekly-collapsed': !weeklyExpender, }">
          <h3>주간 문제</h3>
          <br>
          <WeekView :weeklyExpenderProps="weeklyExpender" v-if="!exnpending"/>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="row">
        <div class="row-3 card-form m-1">
          <div class="myquestion mt-3" >
            <h3>내 질문</h3>
          </div>
        </div>
        
        <div class="row card-form p-3 m-1">
          <RouterLink :to="{'name': 'groupboard'}" class="myquestion mt-3" >
            <h3>질문 게시판</h3>
          </RouterLink>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.custom-btn{
  border: 2px solid lightgray;
  display: flex;
  justify-content: center;
  align-items: center;
}
.custom-btn:hover{
  font-weight: bold;
  transition: all 0.5s;
  border: 2px solid lightgray;

}
.pointer{
  cursor: pointer;
}
/* 헤더 */
#header {
  width: 100%;
  height: 40%;
  display: flex;
  justify-content:space-between;

}
#header>button{
  width: 100px;
  height: 30px;
}

#header h1 {
  font-size: 50px;
  font-weight: bold;
  padding-bottom: 20px;
}
/* 카드 폼  */
.card-form{
  display: flex;
  flex-direction: column;
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.1);
}
/* 아이콘 */
.icon{
  width: 30px;
  height: 30px;
}
.notice-group{
  min-height: 10vh;
}
.rank-group{
  min-height: 10vh;
}

.group-daily-collapsed {
  transition: min-height 0.65s ease-in-out;
  min-height: 6vh;
}

.group-weekly-collapsed {
  transition: min-height 0.65s ease-in-out;
  min-height: 54vh;
}

.shifted {
  transition: min-height 0.65s ease-in-out;
  min-height: 30vh;
}

.col > .row > .card-form{
  min-height: 30vh;
}

.user-progress-bar {
  display: flex;
  flex-direction: column;
}

.user-progress-bar>p {
  flex: 0.5;
}

.user-progress-bar .progress {
  flex: 9.5;
}

/* 이번주 문제 */
.headline{
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.headline .icon{
  width: 20px;
  height: 20px;
}

</style>
