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
    name: 'PointsListView',
    components: {
        VDataTable,
    },
    props: {
        value: Object,
        editMode: Boolean,
        isNew: Boolean
    },
    setup() {
        const headers = ref([
            // 필드 디스크립터를 기반으로 헤더 설정
            { title: "책제목", key: "title" },
            { title: "책 구독비용", key: "readCost" },
            { title: "남은 현재 포인트", key: "pointBalance" },
            { title: "포인트 사용 날짜", key: "pointSpendDate" }
        ]);

        const items = ref([]);

        onMounted(async () => {
            try {
                const response = await axios.get('/pointsLists');
                const data = response.data._embedded.pointsLists;
                data.forEach(obj => {
                    obj.id = obj._links.self.href.split("/").pop();
                });
                items.value = data;
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        });

        return {
            headers,
            items
        };
    }
}
</script>
