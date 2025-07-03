import BaseRepository from './BaseRepository'


export default class AuthorRepository extends BaseRepository {
  constructor(axios) {
    super(axios, 'authors')  // authors 경로 고정
  }

  async updateAuthorApproval(authorId, action) {
    
    const url = `/${this.path}/${action}/${authorId}`;
    return await this.axios.put(this.fixUrl(url));  // 바디 없이 호출
    // return await this.axios.put(this.fixUrl(url), { status }); 이처럼 사용할 수 있음
  }
}
