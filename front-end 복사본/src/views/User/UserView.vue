<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const isMount = ref(false);
const router = useRouter();

const routeLink = (link) => {
  router.push({ name: link });
};

onMounted(() => {
  setTimeout(() => {
    isMount.value = true; // n초 후에 isMount 값을 false로 변경
  }, 100);
});
</script>

<template>
  <header id="header">
    <h1 :class="{ mount: isMount }">My Page</h1>
  </header>
  <div class="user-info p-5">
    <div class="user">
      <font-awesome-icon
        id="icon"
        :icon="['fas', 'circle-user']"
        style="color: #e0e0e0"
        @click="flagStore.toggleUserPopup()"
      />
      <div class="user-edit">
        <h1>Ayaan</h1>
        <span>상명대학교 천안캠퍼스 소프트웨어학과</span>
        <br>
        <br>
        <span>팔로워 0 팔로윙 3</span>
        <div class="user-edit-btn p-4">
          <button>편집</button>
          <button>편집</button>
        </div>
      </div>
    </div>
    <div class="vr p-1"></div>
    <div class="user-record p-4">
      <h1>사이트 활동 기록</h1>
    </div>
  </div>
</template>

<style scoped>
.user-info {
  width: 100%;
  height: 60vh;
  box-shadow: 0 0px 4px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: row;
}
.user {
  width: 40%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
#icon {
  width: 40%;
  height: 40%;
}
.user-edit-btn{
    display: flex;
    justify-content: space-evenly;
    align-items: center;
}
.card-form {
  height: 60vh;
  box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.1);
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
</style>
