<script setup>
import { ref, watch, onMounted } from "vue"
import { useRouter } from 'vue-router'
import { api } from "@/utils/axios"
import { useSessionStore } from "@/stores/session";

//pinia
const sessionStore = useSessionStore();

const isMount = ref(false);
const router = useRouter();

const groupInfo = ref({
    group_name: "",
    group_notice: "",
    group_rule:"",
    image: "",
    group_password:"",
    group_private: false,
    group_max_people:10,
})

const routeLink = (link) => {
  router.push({ group_name: link });
};

const CreateGroup = async () => {
    if(groupInfo.group_name === "" && groupInfo.group_notice === "" && groupInfo.group_rule === "" && groupInfo.group_max_people === ""){
        alert("빈 칸 채우기");
        return;
    }
    

    //비공개
    if(groupInfo.value.group_private){
        const data = { 
            user_token : sessionStore.cookies.get("token"),
            user_email : sessionStore.cookies.get("email"),
            group_name : groupInfo.value.group_name,
            group_rule : groupInfo.value.group_rule,
            group_password : groupInfo.value.group_password,
            group_max_people : groupInfo.value.group_max_people,
            group_private : groupInfo.value.group_private
        }
        console.log(data);
        const result = await api.group.create(data);
        alert(result);
    } else {
        const data = { 
            user_token : sessionStore.cookies.get("token"),
            user_email : sessionStore.cookies.get("email"),
            group_name : groupInfo.value.group_name,
            group_rule : groupInfo.value.group_rule,
            group_password : "",
            group_max_people : groupInfo.value.group_max_people,
            group_private : groupInfo.value.group_private
        }
        console.log(data);
        const result = await api.group.create(data);
        alert(result);
    }
}


onMounted(() => {
    
  setTimeout(() => {
    isMount.value = true; // n초 후에 isMount 값을 false로 변경
  }, 100);
});

watch(() => groupInfo.value.group_max_people, (newValue, oldValue) => {
    console.log("Change detected:", newValue);
    if(newValue <= 1) groupInfo.value.group_max_people = 1;
    if(newValue >= 100) groupInfo.value.group_max_people = 100;
});

</script>
<template>
  <header id="header">
    <h1 :class="{mount : isMount}">그룹 생성</h1>
  </header>
    <!-- 오픈 스터디 게시판 -->
    <div class="main-container card-form p-5">
        <div class="gorup-input-form">
            <label for="InputGroupgroup_name" class="form-label mt-4">그룹 이름</label>
            <input
                type="text"
                class="form-control"
                v-model="groupInfo.group_name"
            />
        </div>
        <div class="gorup-input-form">
            <label for="Inputgroup_notice" class="form-label mt-4">공지사항</label>
            <input
                type="text"
                class="form-control"
                v-model="groupInfo.group_notice"
            />
        </div>
        <div class="gorup-input-form">
             <label for="InputGroupRule" class="form-label mt-4">그룹 조건</label>
             <input
                type="text"
                class="form-control"
                v-model="groupInfo.group_rule"
            />
        </div>
        <div class="gorup-input-form">
          <label class="form-label">그룹 정원</label>
          <input type="range" class="form-range" min="0" max="100" v-model.number="groupInfo.group_max_people">
          <input type="text" v-model="groupInfo.group_max_people">
        </div>
        <div class="group-input-form">
            <input class="form-check-input" type="checkbox" v-model="groupInfo.group_private" id="flexCheckDefault">
            <label class="form-check-label" for="flexCheckDefault">
                공개 / 비공개
            </label>
            <div v-if="groupInfo.group_private" class="group-password-form" >
             <label for="InputGroupPassword" class="form-label mt-4">비밀 번호</label>
                <input type="text" class="form-control" v-model="groupInfo.rule">
            </div>
        </div> 
        
        <div class="group-input-form">
            <button type="submit" class="btn btn-primary" @click="CreateGroup()" >그룹 생성</button>
        </div> 

  </div>
</template>

<style scoped>
/* 메인 */
.main-container{
  display: flex;
  justify-content: left;
  justify-items: center;
  flex-direction: column;
  padding-left: 200px;
}
.card-form{
  height: 80vh;
  box-shadow: 0 0px 8px 0 rgba(0,0,0,0.1);
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

</style>
