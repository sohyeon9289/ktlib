<template>
  <div class="scroll-wrapper">
    <!-- Author ID 입력 -->
    <v-text-field
      label="Author ID"
      v-model.number="value.authorId"
      type="number"
      outlined
      dense
      class="mb-4"
      @blur="onAuthorIdBlur"
    />

    <!-- 자동 표시되는 Author Nickname -->
    <v-text-field
      label="Author Nickname"
      v-model="value.authorNickname"
      readonly
      outlined
      dense
      class="mb-4"
    />

    <!-- 제목 -->
    <v-text-field
      label="Title"
      v-model="value.title"
      outlined
      dense
      class="mb-4"
    />

    <!-- 내용 -->
    <v-textarea
      label="Content"
      v-model="value.content"
      auto-grow
      rows="8"
      outlined
      dense
      class="mb-4"
    />

    <!-- 저장 버튼 -->
    <v-row class="pt-4">
      <v-spacer></v-spacer>
      <v-btn
        color="primary"
        @click="save"
      >
        저장
      </v-btn>
    </v-row>
  </div>
</template>

<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
  name: 'Manuscript',
  mixins: [BaseEntity],
  props: {
    modelValue: Object
  },
  data: () => ({
    path: 'manuscripts',
    value: {
      title: '',
      content: '',
      authorId: null,
      authorNickname: '',
      createdDate: null,
      lastModified: null,
      status: 'DRAFT'
    },
    authorOptions: []
  }),
  watch: {
    modelValue: {
      handler(val) {
        this.value = { ...val }
      },
      immediate: true,
      deep: true
    },
    value: {
      handler(val) {
        this.$emit('update:modelValue', val)
      },
      deep: true
    }
  },
  computed: {

  },
  created() {
    this.fetchAuthors();
  },
  methods: {
    async fetchAuthors() {
      try {
        const response = await fetch('/authors');
        const data = await response.json();
        const authorsRaw = data._embedded?.authors || [];

        this.authorOptions = authorsRaw.map(author => {
          const id = Number(author._links.self.href.split('/').pop());
          return {
            authorId: id,
            nickname: author.authorNickname || ''
          };
        });
      } catch (error) {
        console.error('❌ 작가 목록 불러오기 실패:', error);
      }
    },
    onAuthorIdBlur() {
      const matched = this.authorOptions.find(
        a => Number(a.authorId) === Number(this.value.authorId)
      );
      this.value.authorNickname = matched ? matched.nickname : '';
    },
    async save() {
      if (!this.value.createdDate) {
        this.value.createdDate = new Date().toISOString();
      }
      this.value.lastModified = new Date().toISOString();

      try {
        const result = await this.$options.mixins[0].methods.save.call(this);
        console.log('✅ 저장 완료:', result);
        this.$emit('add', this.value);
      } catch (e) {
        console.error('❌ 저장 실패:', e);
      }
    }
  }
}
</script>

<style scoped>
.scroll-wrapper {
  max-height: 80vh;
  overflow-y: auto;
  padding: 24px;
}
</style>
