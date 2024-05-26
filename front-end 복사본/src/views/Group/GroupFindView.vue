<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useSessionStore } from "@/stores/session";
import { api } from "@/utils/axios";

//pinia
const sessionStore = useSessionStore();

const isVisible = ref(false);
const isOpen = ref("모두");
const inputTag = ref("");
const isMount = ref(false);
const router = useRouter();
const selectGroupId = ref(-1);

const SelectGroup = (id) => {
  selectGroupId.value = id;
};

const toggleVisible = () => {
  isOpen.value =
    isOpen.value === "공개"
      ? "비공개"
      : isOpen.value === "비공개"
      ? "모두"
      : "공개";
  console.log(isOpen.value);
  // isVisible.value = !isVisible.value;
};

const routeLink = (link) => {
  router.push({ name: link });
};

const getGroups = async () => {
  const data = {
    user_token: sessionStore.cookies.get("token"),
    user_email: sessionStore.cookies.get("email"),
  };
  groupList.value = await api.group.getAll(data)
};

const groupRegister = async (group) => {
  console.log(group)
  const data = {
    user_token: sessionStore.cookies.get("token"),
    user_email: sessionStore.cookies.get("email"),
    group_id : group.group_id,
    group_password : group.group_private ? group.group_password : " "
  }

  // try{
  //   const result = await api.group.register(data);
  
  // }
    

  
}

const groupList = ref();
onMounted(() => {
  setTimeout(() => {
    isMount.value = true; // n초 후에 isMount 값을 false로 변경
  }, 100);
  getGroups();
});

</script>
<template>
  <header id="header">
    <h1 :class="{ mount: isMount }">그룹 찾기</h1>
  </header>

  <!-- 네비게이션 바-->
  <div class="p-2 mb-2 controller">
    <!-- 태그 -->
    <div class="tag-container">
      <input
        type="text"
        v-model="inputTag"
        class="form-control-plaintext tag-input m-1 p-2"
        placeholder="태그 검색 #OOO #XXX"
      />
      <button class="btn p-2 m-1 custom-btn search-btn">검색</button>
      <button
        class="btn p-2 m-1"
        @click="toggleVisible()"
        :class="{ 'custom-btn': !isVisible }"
      >
        {{ isOpen }}
      </button>
    </div>
    <button
      class="btn p-2 m-1 custom-btn"
      style="justify-self: right"
      @click="routeLink('groupcreate')"
    >
      그룹 생성
    </button>
  </div>

  <!-- 오픈 스터디 게시판 -->
  <div class="main-container">
    <div
      class="grouplist-container card-form"
      :class="{ 'grouplist-container-wide': selectGroupId <= 0 }"
    >
      <!-- 목차 생성 -->
      <div class="row p-3">
        <span class="col-1">번호</span>
        <span class="col text-align-center">이름</span>
        <span class="col-2">태그</span>
        <span class="col-1">정원</span>
      </div>
      <!-- 그룹 종류 -->
      <div class="group-list">
        <div
          v-for="(item, idx) in groupList"
          :key="idx"
          class="group-item"
          @click="SelectGroup(idx)"
        >
          <div class="row p-3 group-find">
            <span class="col-1">
              <span class="group-id">{{ item.group_id }}</span>
            </span>
            <span class="col text-align-center">
              <span class="group-name">{{ item.group_name }}</span>
            </span>
            <span class="col-2">
              <span class="group-tag">#{{ item.group_rule }}</span>
            </span>
            <span class="col-1">
              <span class="group-personnel">{{ item.group_min_people === item.group_max_people ? "FULL" :  item.group_min_people + "/" + item.group_max_people  }}</span>
            </span>
          </div>
        </div>
      </div>
    </div>
    <div
      class="col-4 card-form gruop-info"
      :class="{
        'group-info': selectGroupId > 0,
        'group-info-hidden': selectGroupId < 0,
      }"
    >
    <div class="container mt-4 grop-container" v-if="selectGroupId != -1">
        <div class="groupinfo row">
            <img src="/public/favicon.ico" class="col-2">
            <p class="col">{{ groupList[selectGroupId].group_name}}</p>
            <p class="col-2">{{ groupList[selectGroupId].group_min_people === groupList[selectGroupId].group_max_people ? "FULL" :  groupList[selectGroupId].group_min_people + "/" + groupList[selectGroupId].group_max_people  }}</p>
        </div>
        <div class="notice">
            <hr class="m-0">
            <h1>그룹 공지</h1>
        </div>
        <div class="require">
            <hr class="m-0">
            <h2> 그룹 조건</h2>
            <p>{{ groupList[selectGroupId].group_rule}}</p>
        </div>
        
        <div class="submit">            
            <input class="form-control" style="width:50%; margin-left: 5px; height: 30px;" type="text" placeholder="코드 입력" v-if="groupList[selectGroupId].group_private">
            <button class="btn btn-primary" style="width: 30%; height: 30px; margin-left: 5px; display: flex; justify-content: center; align-items: center" @click="groupRegister(groupList[selectGroupId])">가입 신청</button>
        </div>
    </div>
    </div>
  </div>
</template>

<style scoped>
/* 메인 */
.main-container {
  display: flex;
}

.grouplist-container {
  display: flex;
  flex-direction: column;
  width: 70%;
  transition: all 3s;
}
.grouplist-container-wide {
  width: 100%;
}
.gruop-info {
  margin-left: 0.5%;
  width: 29.5%;
  transition: all 1s;
}
.group-info-hidden {
  margin-left: 0;
  width: 0;
  opacity: 0;
  visibility: width 1s;
}

.group-list {
  overflow-y: auto;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

/* 헤더 */
#header {
  width: 100%;
  height: 40%;
}

h1 {
  font-size: 30px;
  padding-bottom: 20px;
}
.mount {
  font-size: 40px;
  font-weight: bold;
  /* color: rgb(197, 197, 255); */
  transition: font-size 1s ease, color 0.5s ease, font-weight 0.5s ease;
}

/* 그룹 리스트 */
.card-form {
  height: 80vh;
  box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.1);
}
.group-find {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 1px;
  margin-right: 1px;
}
.group-find:hover {
  background-color: rgba(158, 158, 158, 0.1);
  transition: background-color 0.4s;
  cursor: pointer;
}

/* 컨트롤러 */
.controller {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;

  box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.1);
}
.tag-container {
  display: flex;
  flex-direction: row;
}
.tag-input {
  width: 400px;
  margin-right: 20px;
  border-radius: 10px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  text-align: start;
}

.right {
  position: absolute;
  right: 0px;
}

.custom-btn {
  border: 2px solid black;
  transition: all 1s;
  font-weight: bold;
}
.custom-btn:hover {
  background: rgb(90, 90, 240);
  color: white;
  font-size: 18px;
}

/* 그룹 정보 */

.grop-container{
    position: relative;
    display: flex;
    flex-direction: column;
}
.notice{
    height: 40vh;
}
.require{
    height: 30vh;
}
.submit{
    display: flex;
    flex-direction: row;
    justify-content: right;
}
</style>
