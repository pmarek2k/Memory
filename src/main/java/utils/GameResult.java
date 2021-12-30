package utils;

import java.util.Objects;

public final class GameResult {
    private final String nickname;
    private final int score;

    public GameResult(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    public String nickname() {
        return nickname;
    }

    public int score() {
        return score;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GameResult) obj;
        return Objects.equals(this.nickname, that.nickname) &&
                this.score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, score);
    }

    @Override
    public String toString() {
        return "GameResult[" +
                "nickname=" + nickname + ", " +
                "score=" + score + ']';
    }

    /*
    This method is needed to fill the JTable
     */
    public Object[] toArray(){
        return new Object[]{
                nickname, score
        };
    }

}