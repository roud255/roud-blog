<template>
    <div class="bullshit">
        <div class="bullshit__oops">
            温馨提示
        </div>
        <div class="bullshit__headline">
            访问错误！
        </div>
        <div class="bullshit__info">
            对不起，由于手机端的适配问题，您正在访问的页面不支持访问。为了提升您的使用体验，请使用电脑访问。造成不便，敬请谅解！
        </div>
    </div>
</template>

<script>
    export default {
        name: "ErrorComputerModel"
    }
</script>

<style lang="less" scoped>
    .bullshit {
        position: absolute;
        width: 300px;
        padding: 30px 0;
        overflow: hidden;
        top: 40%;
        left: 50%;
        transform: translate(-50%,-50%);
    &__oops {
         font-size: 32px;
         font-weight: bold;
         line-height: 40px;
         color: #1482f0;
         opacity: 0;
         margin-bottom: 20px;
         animation-name: slideUp;
         animation-duration: 0.5s;
         animation-fill-mode: forwards;
     }
    &__headline {
         font-size: 20px;
         line-height: 24px;
         color: #222;
         font-weight: bold;
         opacity: 0;
         margin-bottom: 10px;
         animation-name: slideUp;
         animation-duration: 0.5s;
         animation-delay: 0.1s;
         animation-fill-mode: forwards;
     }
    &__info {
         font-size: 13px;
         line-height: 21px;
         color: grey;
         opacity: 0;
         margin-bottom: 30px;
         animation-name: slideUp;
         animation-duration: 0.5s;
         animation-delay: 0.2s;
         animation-fill-mode: forwards;
     }
    &__return-home {
         display: block;
         float: left;
         width: 110px;
         height: 36px;
         background: #1482f0;
         border-radius: 100px;
         text-align: center;
         color: #ffffff;
         opacity: 0;
         font-size: 14px;
         line-height: 36px;
         cursor: pointer;
         animation-name: slideUp;
         animation-duration: 0.5s;
         animation-delay: 0.3s;
         animation-fill-mode: forwards;
     }
    @keyframes slideUp {
        0% {
            transform: translateY(60px);
            opacity: 0;
        }
        100% {
            transform: translateY(0);
            opacity: 1;
        }
    }
    }
</style>