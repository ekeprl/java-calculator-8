package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {



    // 기본 구분자
    @Test
    void DefaultDelimiters() {
        assertSimpleTest(() -> {
            run("1,2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }


    // 커스텀 구분자 단일
    @Test
    void singleDelimiter() {
        assertSimpleTest(() -> {
            run("//;\\n1;2;3");
            assertThat(output()).contains("결과 : 6");
        });
    }


    // 커스텀 구분자 다수
    @Test
    void multiDelimiters() {
        assertSimpleTest(() -> {
            run("//;>\\n1;2>3");
            assertThat(output()).contains("결과 : 6");
        });
    }


    // 음수 예외
    @Test
    void negativeNumbers() {
        assertThatThrownBy(() -> runException("-1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("음수는 허용되지 않습니다: -1"); // value 값 포함

        // 커스텀 구분자는 "-" 사용 가능하도록
        assertSimpleTest(() -> {
            run("//-\\n1-2-3");
            assertThat(output()).contains("결과 : 6");
        });
    }


    // 비어있는 값
    @Test
    void emptyInput() {
        assertSimpleTest(() -> {
            run("");
            assertThat(output()).contains("결과 : 0");
        });
    }


    // 공백 포함
    @Test
    void blankInput() {
        assertSimpleTest(() -> {
            run(" 1 , 2 :3 ");
            assertThat(output()).contains("결과 : 6");
        });
    }


    // 연속 구분자
    @Test
    void continuousDelimiters() {
        assertSimpleTest(() -> {
            run("1,,2::3");
            assertThat(output()).contains("결과 : 6");
        });
    }


    // 숫자 외 문자 포함
    @Test
    void wordsIgnored() {
        assertSimpleTest(() -> {
            assertThatThrownBy(() -> runException("1,a,2"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("숫자가 아닌 값이 포함되어 있습니다: a");
        });
    }

    // 문자만 입력
    @Test
    void onlyWord() {
        assertSimpleTest(() -> {
            assertThatThrownBy(() -> runException("a,b,c"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("숫자가 아닌 값이 포함되어 있습니다: a");
        });
    }


    // 0 포함
    @Test
    void zeroInput() {
        assertSimpleTest(() -> {
            run("0,1,2");
            assertThat(output()).contains("결과 : 3");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
