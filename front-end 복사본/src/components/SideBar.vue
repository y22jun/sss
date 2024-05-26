<script setup>
import { ref } from "vue";
import { defineEmits } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useFlagStore } from "@/stores/flag";
import { useSessionStore } from "@/stores/session";
import GroupMenu from "./GroupMenu.vue";

// pinia
const flagStore = useFlagStore();
const sessionStore = useSessionStore();

const router = useRouter();
const route = useRoute();
const emit = defineEmits(["emit-menuHandler", "emit-loginHandler"]);
const name = ref("");

const Basic = [
  { name: "home", icon: "house-user", herf: '/' },
];

const activeLink = (link) => {
  console.log(route.path);
  return route.path === link;
}

const routeLink = (link) => {
  router.push({ name: link });
};

function logout() {
  sessionStore.logout();
  router.go({ name: "home" });
}


</script>

<template>
  <div class="l-navbar" :class="{ expander: flagStore.menuFlag }" @mouseover="flagStore.menuFlag = true"
    @mouseleave="flagStore.menuFlag = false">
    <nav class="nav">
      <div class="side-bar-container">
        <div class="user-container">
          <!-- <div class="nav__brand">
            <font-awesome-icon icon="house" class="nav__toggle" @click="routeLink('home')"></font-awesome-icon>
            <a href="#" class="nav__logo">{{ name }}</a>
          </div> -->
          <div class="nav__list">
            <a v-for="(item, index) in Basic" :key="index" :href="item.herf" class="nav__link"
              :class="{ active: activeLink(item.herf) }" @click="routeLink(item.name)">
              <font-awesome-icon :icon=item.icon class="nav__icon"></font-awesome-icon>
              <!-- <ion-icon :name="item.icon" class="nav__icon"></ion-icon> -->
              <span class="nav_name">{{ item.name }}</span>
            </a>
            <!-- 로그인 사용자 그룹 -->
            <GroupMenu />
          </div>
          
          <a class="nav__link" :class="{ active: activeLink(group) }" @click="routeLink('groupboard')">
            <font-awesome-icon icon="comments" class="nav__icon"></font-awesome-icon>
            <span class="nav_name">Comments</span>
          </a>

        </div>
        <div class="login-container">
          <hr />
          <!-- 로그인 버튼 -->
          <a href="#" v-if="!sessionStore.state.isLoggedIn" class="nav__link" @click="flagStore.toggleModal">
            <font-awesome-icon icon="right-to-bracket" class="nav__icon"></font-awesome-icon>
            <span class="nav_name">Log in</span>
          </a>

          <!-- 로그아웃 버튼 -->
          <a href="#" v-else class="nav__link" @click="logout">
            <font-awesome-icon icon="right-from-bracket" class="nav__icon"></font-awesome-icon>
            <span class="nav_name">Log out</span>
          </a>
        </div>
      </div>
    </nav>
  </div>
</template>
<style scoped>
.side-bar-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.login-container {
  justify-self: flex-end;
}
</style>
