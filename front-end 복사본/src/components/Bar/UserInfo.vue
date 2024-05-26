<script setup>
import { useFlagStore } from '@/stores/flag';
import { useRoute, useRouter } from "vue-router";
import { useSessionStore } from '@/stores/session';

// pinia
const flagStore = useFlagStore();
const sessionStore = useSessionStore();

const router = useRouter();
const route = useRoute();


// 로그 아웃
const LogOut = async () => {
    console.log("로그아웃 시도")
    const email = sessionStore.cookies.get('email');
    const token = sessionStore.cookies.get('token');
    await sessionStore.user_logout(email,token);
    router.go({ name: "home" });
}

</script>

<template>
    <div class="user-popup" :class="{visible : flagStore.userPopupFlag}">
        <div class="user-info">
            <font-awesome-icon id="icon" :icon="['fas', 'circle-user']" style="color: #e0e0e0;"/>
            <p class="m-0 mt-1">이름</p>
            <p class="m-0 mt-1 mb-2">소속</p>
            <p class="m-0 mt-1 mb-2">이메일</p>
            <a href="/user/1" class="mb-3">내 정보 수정</a>

            <button class="btn btn-danger" @click="LogOut()">
                <span class="nav_name">Log out</span>
            </button>
        </div>
    </div>
</template>



<style scoped>

.user-popup{
    position: fixed;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;

    background-color: #ffffff;
    top:8vh;
    right: 12.5%;/* 원하는 거리로 조정 */

    padding: 50px;
    width: 300px;
    height: 400px;
    border-radius: 20px;
    border: 1px solid #ccc;
    box-shadow:  0px 16px 20px rgba(0, 0, 0, 0.1);
    opacity: 0;
    transition: opacity 0.5s ease;
    z-index: -1; 
}
.user-popup.visible {
    opacity: 1;
    z-index: 9999; 
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