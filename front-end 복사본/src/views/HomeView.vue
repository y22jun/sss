<script setup>
import WeekView from "@/components/WeekComponent.vue";
import { onMounted, ref } from "vue";
import { useSessionStore } from "@/stores/session";
import MonacoEditor from 'monaco-editor-vue3'

const sessionStore = useSessionStore();
onMounted(async () => {
  await sessionStore.session()
})

</script>

<template>
  <h1>홈 페이지</h1>
  
</template>

<style scoped>

</style>
