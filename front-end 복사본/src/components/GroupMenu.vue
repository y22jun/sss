<script setup>
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

// 사용자 그룹 확인
const Group = [
  { name: "Group1", icon: "user-group", href: "test" },
  { name: "Group2", icon: "user-group", href: "#" },
];

const activeLink = (link) => {
  console.log("route : ", route.path.substring(1));
  console.log("link: ",link);
  return route.path.substring(1) == link;
}

function routeLink(link){
  router.replace({name : link.href})
};

</script>

<template>
  <hr />
  <a
    v-for="(item, index) in Group"
    :key="index"
    :href="item.href"
    class="nav__link"
    :class="{ active: activeLink(item.href) }"
    @click="routeLink(item)"
  >
    <font-awesome-icon :icon="item.icon" class="nav__icon"></font-awesome-icon>
    <!-- <ion-icon :name="item.icon" class="nav__icon"></ion-icon> -->
    <span class="nav_name">{{ item.name }}</span>
  </a>
</template>
