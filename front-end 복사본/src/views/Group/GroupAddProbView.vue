<script setup>
import { ref, onMounted, watch } from "vue";
import { api } from "@/utils/axios"
import { useRoute } from "vue-router";

const route = useRoute();
const isMount = ref(false);
const weekTitle = ref('');
const probNo = ref('');
const probSource = ref('BOJ');
const problemList = ref([]);
const addProbList = ref({ group_id: route.params.id, group_week_title: weekTitle.value, problemList: [] });

// 문제 찾기
/*
problem 
ㄴ problem_description
ㄴ problem_id
ㄴ problem_image
ㄴ problem_input
ㄴ problem_limit
ㄴ problem_memory_limit
ㄴ problem_output
ㄴ problem_source
ㄴ problem_source_id
ㄴ problem_tier
ㄴ problem_time_limit
ㄴ problem_title
*/
const FindProblem = async () => {
    if (Number.isNaN(Number(probNo.value))) {
        alert('숫자만 입력해주세요.');
        return;
    } else if(problemList.value.find(problem => problem.id === Number(probNo.value))) {
        alert('이미 존재하는 문제입니다.');
        return;
    }else{
        const data = { id : probNo.value }
        try{
            const result = await api.baekjoon.search(data);
            console.log(result.problem_tier);
            const problem = {
                source : probSource.value,
                tier : result.problem_tier,
                title : result.problem_title,
                id : (Number(probNo.value)),
            }
            problemList.value.push(problem);
        } catch{
            alert('존재하지 않는 문제입니다.')
        }        
    }
}
const removeProblem = (removeproblem) =>{
    console.log(removeproblem.id);
    const indexToRemove = problemList.value.findIndex(problem => problem.id == removeproblem.id && problem.source == removeproblem.source);
    console.log(indexToRemove);
    // 요소를 찾았는지 확인합니다.
    if (indexToRemove !== -1) {
        // 찾은 요소를 배열에서 제거합니다.
        problemList.value.splice(indexToRemove, 1);
        console.log('문제가 성공적으로 제거되었습니다.');
    } else {
        console.log('해당하는 문제를 찾을 수 없습니다.');
    }console.log(problemList.value);
}

const AddGroupProblem = async () => {
    if(weekTitle.value === ''){
        alert('제목을 입력해주세요.');
        return;
    }
    for(const problem of problemList.value){
        const addproblem = {problem_source : problem.source , problem_source_id : problem.id , problem_source_title : problem.title}
        addProbList.value.problemList.push(addproblem);
    }
    console.log(addProbList.value);
    const result = await api.group.addGroupProblem(addProbList.value);
}

watch(probSource, (newValue) =>{
    console.log('selected source:', newValue)
})

onMounted(() => {
  setTimeout(() => {
    isMount.value = true; // n초 후에 isMount 값을 false로 변경
  }, 100);
});
</script>

<template>
    <header id="header">
      <h1 :class="{ mount: isMount }">주간 문제 추가</h1>
    </header>
    

    <main class="card-form p-4">
        <label for="title">제목</label>
        <input v-model="weekTitle" type="text" class="form-control mt-2" placeholder="주간 제목 입력">

        
        <label for="title" class="mt-4">문제 추가</label>
        <div class="prob-input-form mt-2">
            <select class="form-control m-1"  style="width: 10vh; height: 4vh;" v-model="probSource">
                <option value="BOJ" >BOJ</option>
                <option value="SWEA">SWEA</option>
            </select>
            <input type="text" v-model="probNo" class="form-control m-1" placeholder="문제 번호 입력"  style="height: 4vh;">
            <button class="btn custom-btn  m-1" style="width: 10vh; height: 4vh;" @click="FindProblem()">추가</button>
        </div>
        <section class="prob__section m-1">
            <!-- 선택된 문제 섹션 -->
            <div v-for="problem in problemList" :key="problem.id">
                <div class="problem__item">
                    <div>{{ problem.source }}</div>
                    <div>{{ problem.tier }}</div>
                    <div>{{ problem.title }}</div>
                    <button class="btn btn-danger" @click="removeProblem(problem)">삭제</button>
                </div>
            </div>
        </section>
        <div class="bottom__div">
            <button class="btn custom-btn  m-1" style="width: 10vh; height: 4vh;" @click="AddGroupProblem()">생성</button>
        </div>
    </main>
</template>

<style scoped>
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

/* 메인 */
.main-container{
    
}
.card-form {
  height: 70vh;
  box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.1);
}

.prob-input-form{
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
}

.custom-btn {
  border: 1px solid lightgray;
  transition: all 1s;
  font-weight: bold;
}
.custom-btn:hover {
  background: rgb(90, 90, 240);
  color: white;
  font-size: 18px;
}

.prob__section{
    border: 2px solid lightgray;
    border-radius: 10px;
    height: 40vh;
}

.bottom__div{
    display: flex;
    flex-direction: row;
    justify-content: right;
    align-items: center;
}

/* 문제 리스트 */
.prob__section {
  /* 섹션의 스타일을 지정합니다. */
  height: 300px; /* 섹션의 높이를 지정합니다. */
  overflow-y: auto; /* 섹션 내용이 넘칠 경우 수직 스크롤이 표시되도록 합니다. */
}

.prob__container {
  /* 내부 컨텐츠를 감싸는 컨테이너의 스타일을 지정합니다. */
}

.problem__item {
  /* 각 문제 아이템의 스타일을 지정합니다. */
  padding: 10px;
  border-bottom: 1px solid #ddd; /* 각 문제 아이템 사이에 구분선을 추가합니다. */
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>