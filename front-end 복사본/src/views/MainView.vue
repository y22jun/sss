<script setup>
import { onMounted, ref } from "vue";
import { useSessionStore } from "@/stores/session";

const sessionStore = useSessionStore();
onMounted(async () => {
  await sessionStore.session()
})
</script>

<template>
    메인페이지
</template>

<style scoped>

</style>
