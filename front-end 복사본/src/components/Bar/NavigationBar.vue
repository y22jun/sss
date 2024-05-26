<script setup>
import { useSessionStore } from '@/stores/session';
import { useFlagStore } from '@/stores/flag';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import SideBar from '@/components/Bar/SideBar.vue'

// 하위 컴포넌트
import UserInfo from '@/components/Bar/UserInfo.vue';

// Pinia
const flagStore = useFlagStore();
const sessionStore = useSessionStore();

const router = useRouter();
const activeLink = (link) => {
  console.log(route.path);
  return route.path === link;
}

const routeLink = (link) => {
  router.push({ name: link });
};

const menuList = ref([
    { name : "Univ", href:"univ", active: false},
    { name : "Group", href:"groupfind", active: false},
]);

const setActive = (item, isActive) => {
  item.active = isActive;
}
const getActive = (item) => {
    return item.active;
}
const getName = (item) => {
    return item.name;
}

</script>

<template>
    <header>
        <div id="header">
            <div class="image" @click="routeLink('home')"><img id="logo" src="/logo-white.jpg" alt="sofit-logo" style="justify-self: center;"></div>
            <div id="menu">
                <div v-for="(item, idx) in menuList" :key="idx">
                    <div class="inner-menu" @mouseover="setActive(item,true)" @mouseleave="setActive(item,false)" @click="routeLink(item.href)">
                        <span :class="{ select : getActive(item)}" >{{item.name}}</span>
                    </div>
                </div>
            </div>

            <div v-if="sessionStore.state.isLoggedIn" id="user" >
                <div class="bell">
                    <font-awesome-icon id="bell-icon" :icon="['fas', 'bell']" style="color: #e0e0e0;" @click="flagStore.toggleUserPopup()"/>
                    <span id="notice">
                        1
                    </span>
                </div>
                <font-awesome-icon id="icon" :icon="['fas', 'circle-user']" style="color: #e0e0e0;" @click="flagStore.toggleUserPopup()"/>
            </div>
            <div v-else>
                <span class="login-btn" @click="flagStore.toggleModal" >Login</span>
            </div>
        </div>
    </header>

    <!--  -->
    
    <UserInfo/>
    <!-- 아주 아주 중요한 버튼 누르면 좆대는거야 -->
    <button @click="flagStore.toggleBookmarkOption()">dkjdsalk</button>
<!-- 
    <div class="bookmark" :class="{ 'bookmark-left': flagStore.bookmarkOptionFlag, 'bookmark-right': !flagStore.bookmarkOptionFlag }" @mouseover="flagStore.bookmarkFlag = true">
        <div class="vr line"></div>
    </div>
    
    <div class="bookmark-drop" :class="{'shifted' : flagStore.bookmarkFlag, 'left' : flagStore.bookmarkOptionFlag, 'right': !flagStore.bookmarkOptionFlag}" @mouseover="flagStore.bookmarkFlag = true" @mouseleave="flagStore.bookmarkFlag = false">
        <p class="myGroup mt-4">My Group</p>
        <div class="bookmark-item m-2 p-4" @click="routeLink('test')">좆댔어</div>
        <div class="bookmark-item m-2 p-4" @click="routeLink('test')">좆댔어</div>
    </div> -->

    <SideBar v-if="sessionStore.state.isLoggedIn" />
</template>


<style scoped>
.login-btn{
    font-size: 20px;
    transition: color 0.5s, font-size 0.5s, font-weight 0.5s;
}
.login-btn:hover{
    font-size: 22px;
    font-weight: bold;
    cursor: pointer;
    color: rgb(90, 90, 240);
}

p{
    margin:0;
}
header{
    position: fixed;
    top: 0;
    left : 0;
    background-color: white;
    width: 100%;
    height: 7vh;
    z-index: 99999 !important;
}
#header{
    margin-top: 1%;
    margin-left: 15%;
    margin-right: 16%;
    display:flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

}
#logo{
    width: 150px;
    height: 40px;
    cursor: pointer;
}


/* 메뉴 관련 */
#menu{
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
}
#menu > p{
    margin: 50px;
    font-size: 30px;
}
.inner-menu{
    position: relative;
    text-align: center;
    margin-top: 10px;
    width: 200px;
    height: 50px;
    font-size: 25px;
}
.inner-menu:hover {
    transition: color 0.5s, font-weight 0.5s, font-size 0.5s; /* 밑줄에 대한 트랜지션 추가 */
    color: rgb(90, 90, 240);
    position: relative; /* 상대적 위치 설정 */
    font-size: 28px;
    font-weight: bold;
    cursor: pointer;
}

.select::after {
  content: ''; /* 가상 요소 생성 */
  position: absolute; /* 절대적 위치 설정 */
  left: 0; /* 왼쪽으로 정렬 */
  bottom: -2px; /* 밑줄을 글자 아래로 이동 */
  width: 0px; /* 요소의 전체 너비 */
  height: 2px; /* 밑줄 높이 */
  border-radius: 100px;
  background-color: rgb(90, 90, 240); /* 밑줄 색상 */
  transition: width 0.5s; /* 밑줄 너비에 대한 트랜지션 추가 */
}

.inner-menu .select:hover::after {
  width: 200px;
}

#user{
    display: flex;
    justify-content: center;
    align-items: center;
}

.bell{
    position: relative;
}
#icon{
    width: 50px;
    height: 50px;
}
#bell-icon{
    width: 30px;
    height: 30px;
    margin-right: 20px;
}
#notice{
    position: absolute;
    top: -40%;
    left: -20%;
    border: 1px solid lightgray;
    border-radius: 50px;
    background-color: rgb(255, 67, 67);
    font-size: 15px;
    min-width: 30px;
    color: white;
    text-align: center;
}

</style>