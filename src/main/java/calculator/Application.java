package calculator;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Application {
    public static void main(String[] args) {
        var input = getInput();
        var numbers = getSplitNumbers(input);

        calculate(numbers);
    }

    //추출값 덧셈
    private static void calculate(String[] numbers) {
        int sum = 0;
        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }

        System.out.println("결과 : " + sum);
    }

    //입력값 & 검증
    private static String getInput() {
        System.out.println("문자열을 입력해 주세요.");
        var input = Console.readLine();

        //숫자앞에 "-"가 붙었을때(음수일때만 포함하도록 수정)
        if (input.matches(".*[^0-9]-[0-9].*") || input.matches("^-\\d.*")) {
            throw new IllegalArgumentException("음수는 허용되지 않습니다.");
        }

        if (input.isEmpty()) {
            return "0";
        }
        return input;
    }


    private static String[] getSplitNumbers(String input) {
        // 1. 기본 구분자 목록
        StringBuilder delimiters = new StringBuilder("[,:");

        // 2. 커스텀 구분자 처리
        if (input.startsWith("//")) {
            // "//" 이후 구간을 개행(혹은 '\n', "\\n") 기준으로 split
            String[] sections = input.split("\\\\n|\n", 2);
            if (sections.length > 1) {
                String custom = sections[0].substring(2); // "//" 제거
                input = sections[1]; // 개행 뒤의 본문

                for (char c : custom.toCharArray()) {
                    if (!Character.isLetterOrDigit(c)) {
                        delimiters.append(Pattern.quote(String.valueOf(c)));
                    }
                }
            }
        }

        delimiters.append("]");

        // 4. 구분자를 기준으로 split
        String[] parts = input.split(delimiters.toString());

        // 5. 숫자만 필터링 (빈 문자열이나 비숫자 제외)
        List<String> numbers = new ArrayList<>();
        for (String part : parts) {
            part = part.trim();
            if (part.matches("-?\\d+")) { // 음수 포함 숫자만
                numbers.add(part);
            }
        }

        return numbers.toArray(new String[0]);
    }

}

