package core.utils.others;

import com.alibaba.excel.EasyExcel;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/26 14:50
 */
public class ExcelUtils {

    @SneakyThrows
    @Test
    public void testExportExcel() {
        String fileName = "test.xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        DemoData test = new DemoData("test");
        List<DemoData> list = new ArrayList<>();
        list.add(test);

        EasyExcel.write(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        }).sheet("模板").doWrite(list);
    }


    @Test
    /**
     * https://blog.csdn.net/delongcpp/article/details/8435656
     * 获取当天 月份天数
     */
    public void testMonthDay() {
        Calendar instance = Calendar.getInstance();
        Integer month = 1;
        instance.set(Calendar.MONTH, month - 1);
        instance.set(Calendar.DATE, 1);
        instance.roll(Calendar.DATE, -1);
        int days = instance.get(Calendar.DATE);
        for (int i = 1; i <= days; i++) {
            System.out.println(i);
        }
    }

    @Test
    public void testMonth() {
        Month month = Month.of(2);
        System.out.println(month.getValue());
    }

    @Test
    public void testStringSplit(){
        String today = "2022-06";
        String[] split = today.split("-");
        System.out.println(Integer.valueOf(split[1]));
    }

    @Test
    public void testDateTime(){
        LocalDate parse = LocalDate.parse("2022-08-01", DateTimeFormatter.ISO_DATE);
        System.out.println(parse);
    }

    @Test
    public void testSb(){
        StringBuilder baseBuffer = new StringBuilder("1");
        StringBuilder s2=  baseBuffer.append("2");
        StringBuilder s3=  baseBuffer.append("3");
        System.out.println(baseBuffer);
        System.out.println(s2);
        System.out.println(s3);
    }

    @Test
    public void testArray(){
        ArrayList<String> objects = new ArrayList<>();
        objects.add("1");
        objects.add(null);
        objects.add("2");
        objects.stream().forEach(r -> System.out.println(r));
    }

    @Test
    public void testTime(){
        String s = "2022-08-03T08:28:10";
        System.out.println(s.substring(s.length() - 8 , s.length() - 3));
    }
}