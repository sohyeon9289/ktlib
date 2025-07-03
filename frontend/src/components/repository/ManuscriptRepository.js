import BaseRepository from './BaseRepository'


export default class ManuscriptRepository extends BaseRepository {
  constructor(axios) {
    super(axios, 'manuscripts')  // authors 경로 고정
  }

  async publishBook(bookId) {
    
    const url = `/${this.path}/publish/${bookId}`;
    return await this.axios.put(this.fixUrl(url));  // 바디 없이 호출
    // return await this.axios.put(this.fixUrl(url), { status }); 이처럼 사용할 수 있음
  }
}
