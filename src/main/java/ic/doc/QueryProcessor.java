package ic.doc;

public class QueryProcessor {

    public String process(String query) {
        StringBuilder results = new StringBuilder();

        switch (query.toLowerCase()) {
            case "shakespeare":
                results.append("William Shakespeare (26 April 1564 - 23 April 1616) was an\n" +
                    "English poet, playwright, and actor, widely regarded as the greatest\n" +
                    "writer in the English language and the world's pre-eminent dramatist. \n");
                break;
            case "asimov":
                results.append("Isaac Asimov (2 January 1920 - 6 April 1992) was an\n" +
                    "American writer and professor of Biochemistry, famous for\n" +
                    "his works of hard science fiction and popular science. \n");
                break;

            case "trang":
                results.append("Quynh Trang Pham (DoB: 19 September 1998) is a professional mess.\n" +
                    "Proficient in any programming language you can think of.\n" +
                    "Would lay down her life for Rooster Teeth\n");
                break;

            case "amina":
                results.append("Amina Navasudeen (DoB: 19 June 1999) is proficient in any language you can think of. \n" +
                    "Loves BTS more than she loves herself.\n");
                break;

            case "yiming":
                results.append("Yiming Xie (DoB: 12 January 1999) is proficient in doing nothing. \n" +
                    "Enjoys singing out loud. Obsessed with KFC original recipe chicken.\n");
                break;

            case "helen":
                results.append("Huilun Tao (DoB: 06 October 1998) is a master of procrastination.\n" +
                    "Enjoys last minute cramming. Obsessed with KFC hot wings.\n");
                break;

            default:
                String content = new WebScraper(query).getContent();
                if (content != null) {
                    results.append(content);
                }
                return results.toString();
        }

        results.append(System.lineSeparator());
        return results.toString();
    }
}