package hello.advanced.trace;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class TraceId {

    private final String id;
    private final int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    public TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        // 무작위 UUID 를 만든 후 0~7 인덱스까지 8자리만 추출
        return UUID.randomUUID().toString().substring(0, 8);
    }

    // 동일 ID  를 지니며 Level 만 하나씩 증가 (depth)
    public TraceId createNextTracId() {
        return new TraceId(id, level+1);
    }

    public TraceId createPreviousId() {
        if (level < 0) {
            throw new IllegalArgumentException("Level 값이 음수가 될 수 없습니다.");
        }
       return new TraceId(id, level-1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }
}
