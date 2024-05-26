<script setup>
import { useFlagStore } from '@/stores/flag';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import { useSessionStore } from '@/stores/session';
import { api } from "@/utils/axios"

// Pinia
const flagStore = useFlagStore();
const sessionStore = useSessionStore();

const groupList = ref();
const router = useRouter();
const routeLink = (group_id) => {
  router.push({ name: 'group', params: { id: group_id } });
};

const getGroups = async () => {
  const data = {
    user_token: sessionStore.cookies.get("token"),
    user_email: sessionStore.cookies.get("email"),
  };
  groupList.value = await api.group.getOne(data);
  console.log("gruopList");
  console.log(groupList.value);
};

getGroups();
</script>

<template>    
    <div class="bookmark" :class="{ 'bookmark-left': flagStore.bookmarkOptionFlag, 'bookmark-right': !flagStore.bookmarkOptionFlag }" @mouseover="flagStore.bookmarkFlag = true">
        <div class="vr line"></div>
    </div>
    <div class="bookmark-drop" :class="{'shifted' : flagStore.bookmarkFlag, 'left' : flagStore.bookmarkOptionFlag, 'right': !flagStore.bookmarkOptionFlag}" @mouseover="flagStore.bookmarkFlag = true" @mouseleave="flagStore.bookmarkFlag = false">
        <p class="myGroup mt-4">My Group</p>
        <div
          v-for="(item, idx) in groupList"
          :key="idx"
          class="group-item"
          @click="routeLink(item.group_id)"
        >
            <span class="bookmark-item m-2 p-4">{{ item.group_name }}</span>
        </div>
    </div>
</template>

<style scoped>

.myGroup{
    text-align: center;
    font-size: 25px;
    margin-top: 20px;
    margin-bottom: 40px;
}

.group-item{
    cursor: pointer;
}
.line{
    width: 5px;
    height: 100px;
    margin-left: 10px;
    border-radius: 30px;
    opacity:0.7;
    box-shadow:  0px 20px 20px rgba(0, 0, 0, 0.1);
    align-self: center;
}
.bookmark{
    height: 100%;
    width: 30px;
    display: flex;
}
.bookmark-left{
    position: fixed;
    justify-content: start;
    top: 50%;
    left: 0; /* 왼쪽에 붙이기 */
    transform: translateY(-50%); /* 수직 중앙 정렬 */
}
.bookmark-right{
    position: fixed;
    justify-content: end;
    top: 50%;
    right: 0; /* 오른쪽에 붙이기 */
    transform: translateY(-50%); /* 수직 중앙 정렬 */
}
.bookmark-right .line{
    margin-left: 0px;
    margin-right: 10px;
}

.bookmark-drop {
    position: fixed;
    top: 50%;
    left: -300px;
    transform: translateY(-50%);
    width: 250px;
    height: 100%;
    background-color: white;
    box-shadow:  0px 20px 20px rgba(0, 0, 0, 0.1);
    transition: left 0.7s; /* 왼쪽 위치 변화에 대한 transition 추가 */
    z-index: 99999;
}
.bookmark-drop.left {
    left: -300px;
    right: auto; /* right 속성을 auto로 설정하여 왼쪽 위치에 고정 */
}
.bookmark-drop.right {
    left: auto; /* left 속성을 auto로 설정하여 오른쪽 위치에 고정 */
    right: -300px;
}


.bookmark-drop.shifted {
    display: block; /* bookmarkFlag가 true일 때 표시 */
}

.bookmark-item{
    border-radius: 10px;
    width: 230px;
    height: 50px;
    background-color: white;
    transition: background-color 0.7s;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
}
.bookmark-item:hover{
    background-color: lightgray;
    transition: background-color 0.7s;
}

.shifted.left {
    left: 0px;
    transition: left 0.7s; /* 왼쪽 위치 변화에 대한 transition 추가 */
}
.shifted.right {
    right: 0px;
    transition: right 0.7s; /* 왼쪽 위치 변화에 대한 transition 추가 */
}
</style>