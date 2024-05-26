<script setup>
import * as monaco from 'monaco-editor/esm/vs/editor/editor.api'
import { ref, onMounted } from 'vue'
// @ts-ignore
import customLangMonarch from '@/utils/custom-lang-monarch'

monaco.languages.register({ id: 'custom' })
monaco.languages.setMonarchTokensProvider('custom', customLangMonarch)

const editor = ref()
let USER_CODE = ref([])
const getUserCode = () => {
    console.log(USER_CODE.value)

}
onMounted(() => {
    let EDITER = monaco.editor.create(editor.value, {
    value: `// Type source code in your language here...`,
    isShowTooltips:"true",
    theme: 'vs-light',
    automaticLayout: true,
    language: 'custom',
    autoIndent: true,
    wordWrap: 'wordWrapColumn',
    wordWrapColumn: 100,
    scrollBeyondLastLine: false,

  })
  
    EDITER.onDidChangeModelContent(() => {
        USER_CODE.value = EDITER.getValue();
        console.log(EDITER.getValue());
    });
})
</script>

<template>
    <button type="button" class="btn btn-primary" @click="getUserCode()">Test</button>
  <div id="editor" ref="editor"></div>
</template>

<style scoped>
#editor {
  width: 100vw;
  height: 100vh;
}
</style>
