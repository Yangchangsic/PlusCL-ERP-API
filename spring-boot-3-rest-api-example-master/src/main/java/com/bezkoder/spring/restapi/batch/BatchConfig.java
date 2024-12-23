package com.bezkoder.spring.restapi.batch;




/*@Configuration
@EnableBatchProcessing
@EnableScheduling*/
public class BatchConfig {/*
							 * 
							 * @Autowired private JobBuilderFactory jobBuilderFactory;
							 * 
							 * @Autowired private StepBuilderFactory stepBuilderFactory;
							 * 
							 * @Autowired private JobLauncher jobLauncher;
							 * 
							 * @Bean public ItemReader<String> reader() { return () -> "Row1,Row2,Row3"; //
							 * 예제 데이터 (쉼표로 구분된 값) }
							 * 
							 * @Bean public ItemProcessor<String, String[]> processor() { return data ->
							 * data.split(","); // 데이터를 배열로 변환 }
							 * 
							 * @Bean public ItemWriter<String[]> writer() { return rows -> { try
							 * (XSSFWorkbook workbook = new XSSFWorkbook()) { var sheet =
							 * workbook.createSheet("Data");
							 * 
							 * int rowIndex = 0; for (String[] row : rows) { var excelRow =
							 * sheet.createRow(rowIndex++); for (int cellIndex = 0; cellIndex < row.length;
							 * cellIndex++) { excelRow.createCell(cellIndex).setCellValue(row[cellIndex]); }
							 * }
							 * 
							 * try (FileOutputStream outputStream = new FileOutputStream("output.xlsx")) {
							 * workbook.write(outputStream);
							 * System.out.println("Excel file generated: output.xlsx"); } } }; }
							 * 
							 * @Bean public Step excelStep() { return stepBuilderFactory.get("excelStep")
							 * .<String, String[]>chunk(1) .reader(reader()) .processor(processor())
							 * .writer(writer()) .build(); }
							 * 
							 * @Bean public Job excelJob() { return jobBuilderFactory.get("excelJob")
							 * .start(excelStep()) .build(); }
							 */
}