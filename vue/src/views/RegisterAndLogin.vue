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
    </div>

</template>

<script>
    // import Register from "../components/Register";
    // import Login from "../components/Login";

    export default {
        name: "RegisterAndLogin",
        components:{
            // Login,
            // Register
        }
    }

</script>

<style scoped>
    @import '../assets/css/reg_login.css';
</style>