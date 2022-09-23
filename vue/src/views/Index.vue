<template>
    <div style="position: relative;min-height:100vh;min-width: 758px;overflow: hidden;">
        <div class="bg-container">
        </div>
        <div style="position: absolute;
                    width: 480px;
                    height: 632px;
                    top: 50%;
                    left: 50%;
                    transform: translate(-50%, -50%);
                    background-color: rgba(255,255,255,.9);
                    border-radius: 16px;
                    overflow: hidden;
                    padding: 50px 50px;">
            <router-view></router-view>
        </div>
        <p class="copyright">Copyright © 2022 roud.top. All rights reserved</p>
    </div>

</template>

<script>

    export default {
        name: "Index",
        components:{

        }
    }

</script>

<style scoped>
    @import '../assets/css/reg_login.css';
</style>