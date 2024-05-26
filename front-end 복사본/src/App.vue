<script setup>
import "@/assets/main.css";
import { ref, onMounted, watch, onUpdated } from "vue";
import TestBar from "@/components/Bar/NavigationBar.vue";
import LoginForm from "@/components/LoginForm.vue";
import { useFlagStore } from "@/stores/flag";
import { useSessionStore } from "@/stores/session";
import { RouterView } from "vue-router";
import { useWindowSize } from '@vueuse/core'


// pinia
const flagStore = useFlagStore();
const sessionStore = useSessionStore();


const { width, height } = useWindowSize()
const pl = ref("11%");
const pr = ref("15%");
onMounted(async () => {
  await sessionStore.session();
})

const isMenuExpanded = ref(false);
const isloginFormOpen = ref(false);

const isLoginModal = () => {
  console.log(isloginFormOpen.value);
  isloginFormOpen.value = !isloginFormOpen.value;
}

function handleMenuExpandedChange(value) {
  isMenuExpanded.value = value;
}
watch(width, () => {

  if(width.value >= 1920){ //전체화면 가정
    pl.value = "11%";
    pr.value = "15%";
  } else {
    pl.value = "5%";
    pr.value = "6%";
  }
})

onMounted(async () => {
  await sessionStore.session();
})

</script>

<template>
  <TestBar />
  <!-- <SideBar @emit-MenuHandler="handleMenuExpandedChange" @emit-loginHandler="flagStore.menuFlag"/> -->
  <div class="app-main">

    <!-- 로그인 폼 모달 -->
    <div v-if="flagStore.modalFlag" class="modal-overlay">
      <LoginForm />
      <button @click="isLoginModal"></button>
    </div>

      <div class="main-container">
        <RouterView></RouterView> 
      </div>
  </div>
</template>

<style scoped>

.app-main {
  background-color: v-bind(color);
    padding-left: v-bind(pl);
    padding-right: v-bind(pr);
    transition: all 1s;
  }

.main-container{
  min-height: 80vh;
  padding-bottom: 300px;
}
/* 모달 오버레이 스타일 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 모달 스타일 */
.modal {
  background: white;
  border: 1px solid #ccc;
  padding: 20px;
}

/* 모달 내부 버튼 스타일 */
.modal button {
  margin-top: 10px;
}


</style>
