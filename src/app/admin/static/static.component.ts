import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Chart, CategoryScale, LinearScale, BarElement, BarController, Title, Tooltip, Legend } from 'chart.js';
import { StatisticsService } from '../../service/StatisticsService';

@Component({
  selector: 'app-static',
  standalone: true,
  templateUrl: './static.component.html',
  styleUrls: ['./static.component.scss']
})
export class StaticComponent implements OnInit {
  @ViewChild('booksChart') booksChartRef!: ElementRef;
  @ViewChild('usersChart') usersChartRef!: ElementRef;
  @ViewChild('categoriesChart') categoriesChartRef!: ElementRef;

  // Biến lưu biểu đồ
  booksChart!: Chart;
  usersChart!: Chart;
  categoriesChart!: Chart;

  // Biến để chứa dữ liệu
  booksChartData: number[] = [];
  usersChartData: number[] = [];
  categoriesChartData: number[] = [];

  constructor(private statisticsService: StatisticsService) {}

  ngOnInit(): void {
    // Đăng ký các thành phần cần thiết của Chart.js
    Chart.register(CategoryScale, LinearScale, BarElement, BarController, Title, Tooltip, Legend);
    // Lấy dữ liệu từ API khi component khởi tạo
    this.getStatisticsData();
  }

  // Lấy dữ liệu từ các API
  getStatisticsData(): void {
    this.statisticsService.getBooksStatistics().subscribe((data: any) => {
      this.booksChartData = [data.quantityBooks, data.booksAddedToday, data.totalBooks];
      this.updateChart('booksChart', this.booksChartRef, this.booksChartData, ['Sách có sẵn', 'Sách thêm hôm nay', 'Tổng số sách'], '#3e95cd');
    });

    this.statisticsService.getUsersStatistics().subscribe((data: any) => {
      this.usersChartData = [data.totalUsers, data.activeUsers, data.usersAddedToday];
      this.updateChart('usersChart', this.usersChartRef, this.usersChartData, ['Tổng số người dùng', 'Người dùng hoạt động', 'Người dùng thêm hôm nay'], '#8e5ea2');
    });

    this.statisticsService.getCategoriesStatistics().subscribe((data: any) => {
      this.categoriesChartData = [data.totalCategories];
      this.updateChart('categoriesChart', this.categoriesChartRef, this.categoriesChartData, ['Tổng số danh mục'], '#3cba9f');
    });
  }

  // Hàm chung để tạo hoặc cập nhật biểu đồ
  updateChart(chartName: string, chartRef: ElementRef, chartData: number[], labels: string[], color: string): void {
    // Thêm index signature
    const charts: { [key: string]: Chart } = {
      booksChart: this.booksChart,
      usersChart: this.usersChart,
      categoriesChart: this.categoriesChart
    };

    if (charts[chartName]) {
      // Nếu biểu đồ đã tồn tại, cập nhật dữ liệu
      charts[chartName].data.datasets[0].data = chartData;
      charts[chartName].update();
    } else {
      // Tạo biểu đồ mới và lưu trữ vào biến tương ứng
      charts[chartName] = new Chart(chartRef.nativeElement, {
        type: 'bar',
        data: {
          labels: labels,
          datasets: [{
            label: 'Số lượng',
            data: chartData,
            backgroundColor: color,
            borderColor: color,
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          scales: {
            x: { beginAtZero: true },
            y: { beginAtZero: true }
          }
        }});
      };
    }
  }

