<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from 'vue-router'

const isVisible = ref(false);
const isOpen = ref("모두");
const inputTag = ref("");
const isMount = ref(false);
const router = useRouter();
const selectGroupId = ref(-1);

const SelectGroup = (id) => {
  selectGroupId.value = id;
}
const toggleVisible = () => {
  isOpen.value = (isOpen.value === "공개") ? "비공개" : (isOpen.value === "비공개") ? "모두" : "공개";
  console.log(isOpen.value);
  // isVisible.value = !isVisible.value;
}

const routeLink = (link) => {
  router.push({ name: link });
};

onMounted(() => {
  setTimeout(() => {
    isMount.value = true; // n초 후에 isMount 값을 false로 변경
  }, 100);
});


const groupList = [
  {'title' : '조댄자들의 모임1', 'tag' : '#조댓서#조댓서#조댓서#조댓서#조댓서', 'writer' : '최명재', 'createdat' : '2024.03.30', 'views' : '5'},
  {'title' : '조댄자들의 모임1', 'tag' : '#조댓서', 'writer' : '최명재', 'createdat' : '2024.03.30', 'views' : '5'},
  {'title' : '조댄자들의 모임1', 'tag' : '#조댓서', 'writer' : '최명재', 'createdat' : '2024.03.30', 'views' : '5'},
  {'title' : '조댄자들의 모임1', 'tag' : '#조댓서', 'writer' : '최명재', 'createdat' : '2024.03.30', 'views' : '5'},
]

</script>
<template>
  <header id="header">
    <h1 :class="{mount : isMount}">글 작성</h1>
  </header>
  
     <!-- 네비게이션 바-->
    <div class="p-2 mb-2 controller">
        <!-- 태그 -->
        <div class="tag-container">
          <input type="text" v-model="inputTag" class="form-control-plaintext tag-input m-1 p-2 " placeholder="태그 검색 #OOO #XXX">
          <button class="btn p-2 m-1 custom-btn search-btn">검색</button>
          <button class="btn p-2 m-1" @click="toggleVisible()" :class="{'custom-btn' : !isVisible}">{{ isOpen }}</button>
        </div>
        <button class="btn p-2 m-1 custom-btn" style="justify-self: right;" @click="routeLink('create')"> 글 작성</button>
    </div>

    <!-- 오픈 스터디 게시판 -->
    <div class="main-container">
      <div class="grouplist-container-wide card-form">
          <!-- 목차 생성 -->
          <div class="row p-3 group-find">
            <span class=" col-1">No</span>
            <span class=" col">제목</span>
            <span class=" col-2">태그</span>
            <span class=" col-1">게시자</span>
            <span class=" col-1 group-center">게시일자</span>
            <span class=" col-1 group-center">조회수</span>
          </div>
          <!-- 그룹 종류 -->
          <div class="group-list">
            <div v-for="(item, idx) in groupList" :key="idx" class="group-item" @click="SelectGroup()">
              <div class="row p-3 group-find">
                <span class="col-1">
                  <span class="group-idx">{{ idx }}</span>
                </span>
                <span class="col">
                  <span class="group-title">{{ item.title }}</span>
                </span>
                <span class="col-2">
                    <span class="group-tag">{{ item.tag }}</span>
                </span>
                <span class="col-1">
                  <span class="group-personnel">{{ item.writer }}</span>
                </span>
                <span class="col-1 group-center">
                  <span class="group-personnel">{{ item.createdat }}</span>
                </span>
                <span class="col-1 group-center">
                  <span class="group-personnel">{{ item.views }}</span>
                </span>
              </div>
            </div>
          </div>

      </div>
  </div>
</template>

<style scoped>
/* 메인 */
.group-center{
    text-align: center;
}
.main-container{
  display: flex;
}

.grouplist-container{
  display: flex;
  flex-direction: column;
  width: 70%;
  transition: all 3s;
}
.grouplist-container-wide{
  width: 100%;
}

.group-list{
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
.mount{
  font-size: 40px;
  font-weight: bold;
  /* color: rgb(197, 197, 255); */
  transition: font-size 1s ease, color 0.5s ease, font-weight 0.5s ease;
}

/* 그룹 리스트 */
.card-form{
  height: 80vh;
  box-shadow: 0 0px 8px 0 rgba(0,0,0,0.1);
}
.group-find{
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 1px;
  margin-right: 1px;
}
.group-find:hover{
  background-color: rgba(158, 158, 158, 0.1);
  transition: background-color 0.4s;
  cursor: pointer;
}

/* 컨트롤러 */
.controller{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  
  box-shadow: 0 0px 8px 0 rgba(0,0,0,0.1);
}
.tag-container{
  display: flex;
  flex-direction: row;
}
.tag-input{
  width: 400px;
  margin-right: 20px;
  border-radius: 10px;
  border: 1px solid rgba(0,0,0,0.1);
  text-align:start;
}


.right {
  position: absolute;
  right: 0px;
}

.custom-btn{
    border: 2px solid black;
    transition: all 1s;
  font-weight: bold;
}
.custom-btn:hover{
  background:  rgb(90, 90, 240);
  color: white;
  font-size: 18px;
}
</style>
