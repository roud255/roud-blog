<template>
    <router-view/>
</template>

<script>

    import ArticleEditor from "../components/manage/ArticleEditor";
    import ArticleShow from "../components/ArticleShow";

    export default {
        name: "Article",
        components: {
            ArticleEditor,
            ArticleShow
        }
    }
</script>

<style scoped>

</style>