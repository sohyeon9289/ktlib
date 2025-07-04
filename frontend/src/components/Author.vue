<template>
    <div>
        <div>
        <String
            label="이름"
            v-model="value.authorName"
            :editMode="editMode"
            class="mb-2"
        />
        <String
            label="닉네임"
            v-model="value.authorNickname"
            :editMode="editMode"
            class="mb-2"
        />
        <String
            label="연락처"
            v-model="value.phoneNumber"
            :editMode="editMode"
            class="mb-2"
        />
        <String
            label="이메일"
            v-model="value.email"
            :editMode="editMode"
            class="mb-2"
        />
        <String
            label="포트폴리오 URL"
            v-model="value.portfolioUrl"
            :editMode="editMode"
            class="mb-2"
        />
        </div>
        <div class="mb-2">* 등록 신청 시 수정이 불가능합니다</div>
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Author',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "authors",
        value: {
            authorName: '',
            authorNickname: '',
            phoneNumber: '',
            email: '',
            portfolioUrl: '',
        }
    }),
    created(){
    },
    methods: {
    },
}
</script>
