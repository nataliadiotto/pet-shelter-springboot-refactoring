package domain.utils;

public class ConsoleVisuals {

        public static final String RESET = "\033[0m";
        public static final String BLACK = "\033[0;30m";
        public static final String RED = "\033[0;31m";
        public static final String GREEN = "\033[0;32m";
        public static final String YELLOW = "\033[0;33m";
        public static final String BLUE = "\033[0;34m";
        public static final String PURPLE = "\033[0;35m";
        public static final String CYAN = "\033[0;36m";
        public static final String WHITE = "\033[0;37m";

        public static final String BOLD = "\033[1m";
        public static final String UNDERLINE = "\033[4m";


        //BOLD + COLORS
        public static final String BOLD_BLACK = "\033[1;30m";
        public static final String BOLD_RED = "\033[1;31m";
        public static final String BOLD_GREEN = "\033[1;32m";
        public static final String BOLD_YELLOW = "\033[1;33m";
        public static final String BOLD_BLUE = "\033[1;34m";
        public static final String BOLD_PURPLE = "\033[1;35m";
        public static final String BOLD_CYAN = "\033[1;36m";
        public static final String BOLD_WHITE = "\033[1;37m";



        public static void animatedTransition() throws InterruptedException {
                for (int i = 0; i < 3; i++) {
                        Thread.sleep(500);
                        System.out.print(BOLD_YELLOW + "." + RESET);
                }
                System.out.println();
        }



}
