package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Report;

public class ReportValidator {
    public static List<String> validate(Report r) {
        List<String> errors = new ArrayList<String>();

        String title_error = _validate_title(r.getTitle());

        if (!title_error.equals("")) {
            errors.add(title_error);
        }

        String content_error = _validate_content(r.getContent());

        if (!content_error.equals("")) {
            errors.add(content_error);
        }

        return errors;
    }

    private static String _validate_title(String title) {
        if (title == null || title.equals("")) {
            return "タイトルを入力して下さい。";
        }

        return "";
    }

    private static String _validate_content(String content) {
        if (content == null || content.equals("")) {
            return "内容を入力して下さい。";
        }

        return "";
    }
}
