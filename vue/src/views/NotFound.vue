<!--<div class="_404">-->
<!--    <h1>404 NotFound</h1>>-->
<!--</div>-->

<!--<script>-->
<!--    export default {-->
<!--        name: "NF404",-->
<!--        data() {-->
<!--            return {-->
<!--                countDown: 5-->
<!--            }-->
<!--        },-->
<!--        methods: {-->
<!--            goHome() {-->
<!--                this.$router.push('/show')-->
<!--            }-->
<!--        },-->
<!--        created() {-->
<!--            setInterval(() => {-->
<!--                this.countDown > 0 ? this.countDown&#45;&#45; : this.$router.push('/show')-->
<!--            }, 1000)-->
<!--        }-->
<!--    }-->
<!--</script>-->

<template>
    <div class="not-found">
        <img src="http://roud.top/img/404.gif" alt="" />
    </div>
</template>
<script>
    export default {
        name: 'NotFound',
    }
</script>
<style scoped>
    .not-found {
        width: 100vw;
        height: 100vh;
        overflow: hidden;
    }
    .not-found img {
        width: 100%;
        height: 100%;
    }
</style>>
