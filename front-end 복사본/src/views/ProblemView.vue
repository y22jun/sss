<script setup>
import { mount } from "@vue/test-utils";
import { ref, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import { Codemirror } from "vue-codemirror";
import { api } from "@/utils/axios";
import MonacoEditor from "@/components/MonacoEditor.vue";

const route = useRoute();
const language = ref("c");
const probTierOver = ref(false);
const problemData = ref();
const tierColor = ref('white');
function changeLanguage(value){
  language.value = value;
}


const BOJ_TIER_COLOR = {
  'U' : 'rgb(45, 45, 45)',
  'B' : 'rgb(173, 86, 0)',
  'S' : 'rgb(67, 95, 122)',
  'G' : 'rgb(236, 154, 0)',
  'P' : 'rgb(39, 226, 164)',
  'D' : 'rgb(0, 180, 252)',
  'R' : 'rgb(255, 0, 98)',
}

const routeProbPage = () => {
  console.log("route");
}

// 문제 정보
/*
Problem Data
ㄴ long problem_id;
ㄴ String problem_source;
ㄴ long problem_source_id;
ㄴ String problem_title;
ㄴ String problem_description;
ㄴ String problem_tier;
ㄴ String problem_input;
ㄴ String problem_output;
ㄴ String problem_limit;
ㄴ String problem_image;
ㄴ String problem_time_limit;
ㄴ String problem_memory_limit;
*/
const problemInfo = ref({'titleKo' : '', 'tier' : '', 'CorrectRate' : ''});
const SearchProblemInfo = async (id) => {
  const data = { id : id};
  const result = await api.baekjoon.search(data);
  problemData.value = result;
  tierColor.value = BOJ_TIER_COLOR[problemData.value.problem_tier[0]];
  console.log(result);
} 

// 문제 내용 입력, 출력 등
const parsingHTML = ref('LOADING');

onMounted(() => {
  SearchProblemInfo(route.params.id)
})

</script>


<template>
  <header id="header" class="mb-4" style="position: relative;">
    <!-- <img src="../../public/g1.png" id="prob-tier"> -->
    <span id="prob-tier-name" :style="{ 'background-color': tierColor }" @mouseover="probTierOver = true" @mouseleave="probTierOver = false">{{ problemData && problemData.problem_tier }}</span>
    <div>
      <a style="cursor: pointer;" href='https://www.acmicpc.net/problem/1100' id="prob-name" :class="{probLink : true}">{{ problemData && problemData.problem_title}}</a>
      <img id="link-img" @click="routeProbPage()" src="../../public/link.jpg" alt="">
    </div>
    <div class="prob-solve-info p-4 " :class="{show : probTierOver}">
        <div class="prob-item">
          <div class="icon"><font-awesome-icon class="prob-icon " :icon="['fas', 'sd-card']" /></div>
          <span class=" mt-2" style="text-align: center;">{{ problemData && problemData.problem_memory_limit}}</span>
        </div>
        
        <div class="prob-item">
          <div class="icon"><font-awesome-icon class="prob-icon " :icon="['fas', 'hourglass-start']" /></div>
          <span class=" mt-2" style="text-align: center;">{{ problemData && problemData.problem_time_limit }}</span>
        </div>

    </div>
  </header>
  <main>
    <div class="problem-container card-form p-4">
      <!-- 문제 내용 -->
      <h4>문제</h4>
      <span v-if="problemData" v-html="problemData.problem_description"></span>
      <hr>
      <h4>입력</h4>
      <span v-if="problemData" v-html="problemData.problem_input"></span>
      <hr>
      <h4>출력</h4>
      <span v-if="problemData" v-html="problemData.problem_output"></span>
    </div>
    <div class="vr"></div>
    <div class="code-container card-form nav">
      <li class="nav-item dropdown" style="height: 40px; width: 80px;">
        <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">{{language}}</a>
        <ul class="dropdown-menu">
          <li><a class="dropdown-item" @click="changeLanguage('java')" >Java</a></li>
          <li><hr class="dropdown-divider"></li>
          <li><a class="dropdown-item" @click="changeLanguage('python')" >Python</a></li>
          <li><hr class="dropdown-divider"></li>
          <li><a class="dropdown-item" @click="changeLanguage('C')" >C</a></li>
        </ul>
      </li>
      <!-- <textarea ref="code" class="code-view" style="background-color: blue;">
      </textarea> -->
      
      <div class="code-view">
        <MonacoEditor/>
      </div>
    </div>
  </main>
</template>
<style scoped>
@import url("@/assets/3024-day.css");
/* 공용 폼 */
.card-form{
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.1);
}
a {  text-align: center;  text-decoration: none; /* 링크의 밑줄 제거 */  color: inherit; /* 링크의 색상 제거 */}

/* 헤더 */
.prob-item{
  padding: 0;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  text-align: center;
}
.prob-icon{
  width: 30px;
  height: 30px;
}
#prob-tier-name{
  font-size: 20px;
  color: white;
  border-radius: 40px;
  font-weight: bold;
  text-align: center;
  padding: 5px;
  padding-left: 10px;
  padding-right: 10px;
  font-family: "monospace";
  box-shadow: 1px 2px 4px 0 rgba(0,0,0,0.3);
}
#prob-tier-name:hover{
  font-size: 22px;
  transition: font-size 0.5s;
}
#prob-tier{
  max-width: 50px;
  max-height: 50px;
}
#link-img{
  max-width: 30px;
  max-height: 30px;
}

#header {
  width: 100%;
  height: 40%;
  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;
}

#prob-name {
  font-size: 30px;
  font-weight: bold;
  margin-left: 10px;
}
#prob-name:hover{
  transition: font-size 0.5s;
  position: relative;
  font-size: 32px;
}
.probLink::after{
  content: "";
  position: absolute;
  left: 0;
  bottom: -2px;
  width: 0%;
  height: 2px;
  border-radius: 100px;
  background-color: black;
  cursor:pointer;
}
#prob-name:hover.probLink::after{
  width: 110%;
}

.prob-solve-info{
  position: absolute;
  top: 120%;
  left: 10px;
  width: 1px;
  height: 1px;
  z-index: -1;
  background-color: white;
  opacity: 0;
  border-radius: 10px;
  box-shadow: 0 1px 8px 0 rgba(0,0,0,0.3);
  transition: opacity 0.5s, width 1s, height 1s;
}
.prob-solve-info.show{
  z-index:9999;
  opacity: 1;
  width: 200px;
  height: 100px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
}
/* 메인 */
main {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.problem-container {
  margin-right: 1%;
  flex : 5;
}

input{
  border: 1px solid rgb(218, 218, 218);
}
.code-container {
  position:relative;
  display: flex;
  flex-direction: column;
  margin-left: 1%;
  flex : 5;
  height: fit-content;
}
.code-view{
  flex: auto;
  height: 100%;
}
#code-contents{
  font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
.form-controll{
  margin:1%;
  width:98%;
  min-height: 100px;
}

/* 문제 */
#code-contents{
  font-family: "courier", monospace;
  font-optical-sizing: auto;
  font-style: normal;
  color: #418A8A;
}

::v-deep #problem_description{
    color: red;
}

</style>

