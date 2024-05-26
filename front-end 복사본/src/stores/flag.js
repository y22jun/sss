import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useFlagStore = defineStore('flag', () => {
    const modalFlag = ref(false);
    const menuFlag = ref(false);
    const userPopupFlag = ref(false);
    const userGroupInfo = ref(false);
    const bookmarkFlag = ref(false);
    const bookmarkOptionFlag = ref(false);

    function toggleModal(){
        modalFlag.value = !modalFlag.value;
    }

    function toggleUserPopup(){
        userPopupFlag.value = !userPopupFlag.value;
    }

    function toggleGroupPopup(){
        userGroupInfo.value = !userGroupInfo.value;
    }
    
    function toggleBookmark(){
        console.log(bookmarkFlag.value);
        bookmarkFlag.value = !bookmarkFlag.value;
    }

    function toggleBookmarkOption(){
        console.log(bookmarkOptionFlag.value);
        bookmarkOptionFlag.value = !bookmarkOptionFlag.value;
    }

    
    return { modalFlag, toggleModal, menuFlag, userPopupFlag, toggleUserPopup, userGroupInfo, toggleGroupPopup, bookmarkFlag, toggleBookmark, bookmarkOptionFlag, toggleBookmarkOption};
})