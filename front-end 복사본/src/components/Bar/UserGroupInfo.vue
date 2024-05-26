<script setup>
import { useFlagStore } from '@/stores/flag';
import { useRoute, useRouter } from "vue-router";
import { useSessionStore } from '@/stores/session';
// pinia
const flagStore = useFlagStore();
const sessionStore = useSessionStore();

const router = useRouter();
const route = useRoute();
const groupList = ref();

// 로그 아웃
function logout() {
  sessionStore.logout();
  router.go({ name: "home" });
}


</script>


<template>
    <div @click="flagStore.userGroupInfo=false"  @mouseleave="flagStore.userGroupInfo=false" class="user-group-popup" :style="{ opacity: flagStore.userGroupInfo ? 1 : 0 }">
        <RouterLink :to="{'name' : 'test'}">그룹 1</RouterLink>   
        <p>그룹 1</p>
        <p>그룹 1</p>
    </div>
</template>



<style scoped>

.user-group-popup{
    position: absolute;
    background-color: #ffffff;
    top: 50%; /* 부모 요소의 중앙을 기준으로 위에서 50% */
    left: 50%; /* 부모 요소의 중앙을 기준으로 왼쪽에서 50% */
    transform: translate(-50%, 10%);

    display: flex;

    flex-direction: column;
    justify-content: flex-start;
    align-items: center;

    padding: 50px;
    width: 300px;
    height: 400px;
    border-radius: 20px;
    border: 1px solid #ccc;
    box-shadow:  0px 16px 20px rgba(0, 0, 0, 0.1);

    opacity: 0;
    transition: opacity 0.5s ease;
}
.user-popup.visible {
    opacity: 1;
}

.user-info{
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

#icon{
    width:80px;
    height: 80px;
}

</style>