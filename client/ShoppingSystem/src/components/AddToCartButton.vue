<template>
  <div class="button_container">
    <div class="btn">Add To Cart</div>
  </div>
</template>

<script>
  export default {
    name: 'AddToCartButton',
    props: [],
    data () {
      return {
      }
    }

  }
</script>

<style scoped>
  .btn {
    background: #3498DB;
    color: #ffffff;
    padding: 8% 0;
    border-radius: 4px;
    display: block;
    width: auto;
    text-align: center;

    -webkit-user-select: none; /* Safari */
    -moz-user-select: none; /* Firefox */
    -ms-user-select: none; /* IE10+/Edge */
    user-select: none; /* Standard */
  }

  .btn:hover {
    background-color: #2980B9;
  }

  .btn:active {
    background-color: rgb(104, 166, 207);
  }

</style>
