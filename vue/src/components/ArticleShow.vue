<template>
    <div style="height:100%;min-width: 800px; margin: 0 auto;text-align: center">
        <v-md-editor :model-value="markdown" mode="preview"></v-md-editor>
    </div>
</template>

<script>
    import request from "../utils/request";

    export default {
        name : "ArticleShow",
        data() {
            return {
                markdown: '',
            };
        },
        methods:{
            getPostBody(){
                let url = window.location.href;
                let param_str = url.split('?')[1];
                let params = new URLSearchParams('?'+param_str);
                let get_id = params.get('id');
                request.get("/aat/getArticleById",{params:{
                        id : get_id,
                    }}).then(res=>{
                        this.markdown=res.data.postbody;
                })
            }
        },
        created() {
            this.getPostBody();
        }
    };
</script>

<style scoped>

</style>