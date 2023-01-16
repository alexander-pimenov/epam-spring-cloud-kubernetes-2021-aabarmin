package com.naya.exams.apigateway.util;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.nonNull;

/**
 * Утилитарные методы для работы с {@link HttpServletRequest}.
 *
 * @author Ilya Buyluk <69BuylukIV@cbr.ru>
 */
public final class HttpServletUtil {

    /**
     * Закрытый контруктор.
     */
    private HttpServletUtil() {
    }

    /**
     * Метод для получения url корня web приложения
     *
     * @param request запрос
     * @return url домашней страницы в виде строки
     */
    public static String getRootUrl(final HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    /**
     * Метод для инвалидации cookie.
     *
     * @param request  запрос
     * @param response ответ
     */
    public static void deleteAllCookies(final HttpServletRequest request, final HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (nonNull(cookie)) {
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                    response.addCookie(cookie);
                }
            }
        }
    }

    /**
     * Метод для выхода из приложения
     *
     * @param request  запрос
     * @param response ответ
     * @throws ServletException исключение
     */
    public static void logout(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException {
        request.logout();
        deleteAllCookies(request, response);
    }

    /**
     * Метод для получения URL адреса перенаправления на корень web приложения
     *
     * @param redirectUrlPrefix префикс адреса перенаправления
     * @param request           запрос
     * @return URL адрес перенаправления на корень web приложения
     */
    public static String getLogoutUrl(final String redirectUrlPrefix,
            final HttpServletRequest request) {
        return redirectUrlPrefix + getRootUrl(request);
    }
}
