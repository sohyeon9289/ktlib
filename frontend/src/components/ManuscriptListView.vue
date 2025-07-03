<template>
  <v-data-table
    :headers="headers"
    :items="items"
    :items-per-page="5"
    class="elevation-1"
  ></v-data-table>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { VDataTable } from 'vuetify/labs/VDataTable'

export default {
  name: 'ManuscriptListView',
  components: { VDataTable },
  props: {
    value: Object,
    editMode: Boolean,
    isNew: Boolean
  },
  setup() {
    const headers = ref([
      { title: "원고 ID", key: "manuscriptId", sortable: true },
      { title: "제목", key: "title" },
      { title: "내용", key: "content" },
      { title: "작가 ID", key: "authorId" },
      { title: "상태", key: "status" },
      { title: "수정일시", key: "updatedAt" }
    ]);

    const items = ref([]);

    onMounted(async () => {
      try {
        const response = await axios.get('/manuscriptLists');
        const data = response.data._embedded.manuscriptLists;

        data.forEach(obj => {
          obj.manuscriptId = Number(obj._links.self.href.split('/').pop()); // ✅ 핵심
          obj.updatedAt = obj.lastModified || obj.createdDate;
          if (obj.content?.length > 100) {
            obj.content = obj.content.slice(0, 100) + '...';
          }
        });

        items.value = data;
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    });

    return { headers, items };
  }
}
</script>
