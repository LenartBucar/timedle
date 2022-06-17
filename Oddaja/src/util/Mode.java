package util;

public enum Mode {
   SLOW(60_000), MEDIUM(40_000), FAST(20_000);
   final long delay;

    Mode(long delay) {this.delay = delay;}

    public long getDelay(){return delay;}
}
