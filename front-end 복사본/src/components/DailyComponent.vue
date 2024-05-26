<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { api } from "@/utils/axios";

const router = useRouter();
const route = useRoute();
const problemSource = ref("BAEKJOON");
const ProblemNumber = ref();
const problemInfo = ref();
const searching = ref(false);
function changeProblemSource(value) {
  problemSource.value = value;
}
async function searchProblem() {
  searching.value = true;
  // 번호가 숫자인지 확인

  // 문제 조회
  const result = await api.solved.searchOne(ProblemNumber.value);
  console.log(result[0]);

  // setTimeout(() => {
  //   searching.value = false;
  // }, 3000); // 3초 대기
  
  searching.value = false;
  //3초안에 못찾은 경우
}
function moveProblemView(value) {
  router.push({ name: "problem-view", params: { id: value } });
}

</script>

<template>
  <!-- 목차 -->
  <br>
  <div class="problem-items">
    <div class="problem-item" @click="moveProblemView(1000)">
      <div class="img-container">
        <img src="../assets/logo.svg" alt="error-image" class="rank-img" />
      </div>
      <div class="vr"></div>
      <p class="problem-source">출처</p>
      <div class="vr"></div>
      <p class="problem-name">문제 이름</p>
      <div class="vr"></div>
      <!--알고리즘 차후 추가
      <p class="problem-solve-algorithm"> 알고리즘 </p>
      <div class="vr"></div>
      -->
      <p class="problem-solve">품</p>
    </div>

    <div class="problem-item" @click="moveProblemView(1100)">
      <div class="img-container">
        <img src="../assets/logo.svg" alt="error-image" class="rank-img" />
      </div>
      <div class="vr"></div>
      <p class="problem-source">출처</p>
      <div class="vr"></div>
      <p class="problem-name">문제 이름</p>
      <div class="vr"></div>
      <p class="problem-solve">품</p>
    </div>
  </div>
</template>

<style scoped>
.btn-size {
  width: 100px;
}

p {
  margin: 0px 0px 0px 0px;
  line-height: 120%;
}
.problem-items{
  max-height: 20vh;
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
  border-radius: 5px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);

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
.img-container {
  flex: 0.05;
  position: relative;
}
.rank-img {
  vertical-align: 0;
  width: 100%;
  height: 100%;
}
.problem-name {
  flex: 0.85;
  text-align: left;
  margin-left: 1%;
}
.problem-solve-algorithm {
  text-align: center;
  flex: 0.15;
}
.problem-solve {
  flex: 0.05;
}

.right {
  position: absolute;
  right: 0px;
}
.navigation-bar {
  position: relative;
  display: flex;
  overflow: visible;
  flex-direction: row;
  justify-content: left;
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
