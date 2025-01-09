import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service'; // TokenService để lấy token
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {
  private baseUrl = 'http://localhost:8080/api/v1/statistics';

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  // Create header with token from TokenService
  createAuthorizationHeader(): HttpHeaders {
    const token = this.tokenService.getToken(); // Get token from TokenService
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  // Get statistics for categories
  getCategoriesStatistics(): Observable<any> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<any>(`${this.baseUrl}/categories`, { headers });
  }

  // Get statistics for users
  getUsersStatistics(): Observable<any> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<any>(`${this.baseUrl}/users1`, { headers });
  }

  // Get statistics for books
  getBooksStatistics(): Observable<any> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<any>(`${this.baseUrl}/books`, { headers });
  }
}
