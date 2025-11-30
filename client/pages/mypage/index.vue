<template>
  <div>
    <div v-if="userMe">
      <div>
        {{ userMe.name }}さんでログイン中
      </div>
      <div v-if="showTaskIndexLink">
        タスク一覧
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>

const userMe = ref<UserMe>();

interface UserMe {
  sub: string;
  name: string;
  email: string;
  imageUrl: string;
  scope: string;
}

const setUserMe = async (): Promise<void> => {
  const response = await $fetch<{
    me: {
      sub: string;
      name: string;
      email: string;
      image_url: string;
      scope: string;
    }
  }>('/api/user/me');

  userMe.value = {
    sub: response.me.sub,
    name: response.me.name,
    email: response.me.email,
    imageUrl: response.me.image_url,
    scope: response.me.scope,
  }
}

const showTaskIndexLink = computed((): boolean => {
  return userMe.value?.scope.split(' ').includes('task.read') ?? false;
});

onMounted(() => {
  setUserMe();
});

</script>

<style>

</style>