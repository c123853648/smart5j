package com.jinandaxue.entity;

import java.io.Serializable;
import java.util.List;

public class WeatherBean implements Serializable {

    /**
     * time : 2019-04-23 21:57:51
     * cityInfo : {"city":"济南市","cityId":"101120101","parent":"山东","updateTime":"21:17"}
     * date : 20190423
     * message : Success !
     * status : 200
     * data : {"shidu":"81%","pm25":41,"pm10":68,"quality":"良","wendu":"18","ganmao":"极少数敏感人群应减少户外活动","yesterday":{"date":"22","sunrise":"05:31","high":"高温 25.0℃","low":"低温 16.0℃","sunset":"18:51","aqi":127,"ymd":"2019-04-22","week":"星期一","fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},"forecast":[{"date":"23","sunrise":"05:30","high":"高温 23.0℃","low":"低温 16.0℃","sunset":"18:52","aqi":61,"ymd":"2019-04-23","week":"星期二","fx":"南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"24","sunrise":"05:29","high":"高温 24.0℃","low":"低温 8.0℃","sunset":"18:53","aqi":89,"ymd":"2019-04-24","week":"星期三","fx":"北风","fl":"3-4级","type":"雷阵雨","notice":"带好雨具，别在树下躲雨"},{"date":"25","sunrise":"05:27","high":"高温 18.0℃","low":"低温 8.0℃","sunset":"18:54","aqi":56,"ymd":"2019-04-25","week":"星期四","fx":"东北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"26","sunrise":"05:26","high":"高温 19.0℃","low":"低温 11.0℃","sunset":"18:54","aqi":69,"ymd":"2019-04-26","week":"星期五","fx":"东北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"27","sunrise":"05:25","high":"高温 24.0℃","low":"低温 10.0℃","sunset":"18:55","aqi":89,"ymd":"2019-04-27","week":"星期六","fx":"南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"28","sunrise":"05:24","high":"高温 19.0℃","low":"低温 10.0℃","sunset":"18:56","aqi":70,"ymd":"2019-04-28","week":"星期日","fx":"北风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"29","sunrise":"05:22","high":"高温 23.0℃","low":"低温 12.0℃","sunset":"18:57","ymd":"2019-04-29","week":"星期一","fx":"北风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"30","sunrise":"05:21","high":"高温 24.0℃","low":"低温 13.0℃","sunset":"18:58","ymd":"2019-04-30","week":"星期二","fx":"北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"01","sunrise":"05:20","high":"高温 22.0℃","low":"低温 12.0℃","sunset":"18:59","ymd":"2019-05-01","week":"星期三","fx":"西北风","fl":"4-5级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"02","sunrise":"05:19","high":"高温 24.0℃","low":"低温 12.0℃","sunset":"19:00","ymd":"2019-05-02","week":"星期四","fx":"北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"03","sunrise":"05:18","high":"高温 27.0℃","low":"低温 15.0℃","sunset":"19:00","ymd":"2019-05-03","week":"星期五","fx":"西南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"04","sunrise":"05:17","high":"高温 21.0℃","low":"低温 12.0℃","sunset":"19:01","ymd":"2019-05-04","week":"星期六","fx":"东北风","fl":"4-5级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"05","sunrise":"05:16","high":"高温 24.0℃","low":"低温 14.0℃","sunset":"19:02","ymd":"2019-05-05","week":"星期日","fx":"北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"06","sunrise":"05:15","high":"高温 29.0℃","low":"低温 17.0℃","sunset":"19:03","ymd":"2019-05-06","week":"星期一","fx":"南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"07","sunrise":"05:14","high":"高温 31.0℃","low":"低温 18.0℃","sunset":"19:04","ymd":"2019-05-07","week":"星期二","fx":"西南风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"}]}
     */

    private String time;
    private CityInfoBean cityInfo;
    private String date;
    private String message;
    private int status;
    private DataBean data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CityInfoBean getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoBean cityInfo) {
        this.cityInfo = cityInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class CityInfoBean {
        /**
         * city : 济南市
         * cityId : 101120101
         * parent : 山东
         * updateTime : 21:17
         */

        private String city;
        private String cityId;
        private String parent;
        private String updateTime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class DataBean {
        /**
         * shidu : 81%
         * pm25 : 41
         * pm10 : 68
         * quality : 良
         * wendu : 18
         * ganmao : 极少数敏感人群应减少户外活动
         * yesterday : {"date":"22","sunrise":"05:31","high":"高温 25.0℃","low":"低温 16.0℃","sunset":"18:51","aqi":127,"ymd":"2019-04-22","week":"星期一","fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"}
         * forecast : [{"date":"23","sunrise":"05:30","high":"高温 23.0℃","low":"低温 16.0℃","sunset":"18:52","aqi":61,"ymd":"2019-04-23","week":"星期二","fx":"南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"24","sunrise":"05:29","high":"高温 24.0℃","low":"低温 8.0℃","sunset":"18:53","aqi":89,"ymd":"2019-04-24","week":"星期三","fx":"北风","fl":"3-4级","type":"雷阵雨","notice":"带好雨具，别在树下躲雨"},{"date":"25","sunrise":"05:27","high":"高温 18.0℃","low":"低温 8.0℃","sunset":"18:54","aqi":56,"ymd":"2019-04-25","week":"星期四","fx":"东北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"26","sunrise":"05:26","high":"高温 19.0℃","low":"低温 11.0℃","sunset":"18:54","aqi":69,"ymd":"2019-04-26","week":"星期五","fx":"东北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"27","sunrise":"05:25","high":"高温 24.0℃","low":"低温 10.0℃","sunset":"18:55","aqi":89,"ymd":"2019-04-27","week":"星期六","fx":"南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"28","sunrise":"05:24","high":"高温 19.0℃","low":"低温 10.0℃","sunset":"18:56","aqi":70,"ymd":"2019-04-28","week":"星期日","fx":"北风","fl":"<3级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"29","sunrise":"05:22","high":"高温 23.0℃","low":"低温 12.0℃","sunset":"18:57","ymd":"2019-04-29","week":"星期一","fx":"北风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"30","sunrise":"05:21","high":"高温 24.0℃","low":"低温 13.0℃","sunset":"18:58","ymd":"2019-04-30","week":"星期二","fx":"北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"01","sunrise":"05:20","high":"高温 22.0℃","low":"低温 12.0℃","sunset":"18:59","ymd":"2019-05-01","week":"星期三","fx":"西北风","fl":"4-5级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"02","sunrise":"05:19","high":"高温 24.0℃","low":"低温 12.0℃","sunset":"19:00","ymd":"2019-05-02","week":"星期四","fx":"北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"03","sunrise":"05:18","high":"高温 27.0℃","low":"低温 15.0℃","sunset":"19:00","ymd":"2019-05-03","week":"星期五","fx":"西南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"04","sunrise":"05:17","high":"高温 21.0℃","low":"低温 12.0℃","sunset":"19:01","ymd":"2019-05-04","week":"星期六","fx":"东北风","fl":"4-5级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"05","sunrise":"05:16","high":"高温 24.0℃","low":"低温 14.0℃","sunset":"19:02","ymd":"2019-05-05","week":"星期日","fx":"北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"06","sunrise":"05:15","high":"高温 29.0℃","low":"低温 17.0℃","sunset":"19:03","ymd":"2019-05-06","week":"星期一","fx":"南风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"07","sunrise":"05:14","high":"高温 31.0℃","low":"低温 18.0℃","sunset":"19:04","ymd":"2019-05-07","week":"星期二","fx":"西南风","fl":"3-4级","type":"晴","notice":"愿你拥有比阳光明媚的心情"}]
         */

        private String shidu;
        private int pm25;
        private int pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private YesterdayBean yesterday;
        private List<ForecastBean> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 22
             * sunrise : 05:31
             * high : 高温 25.0℃
             * low : 低温 16.0℃
             * sunset : 18:51
             * aqi : 127
             * ymd : 2019-04-22
             * week : 星期一
             * fx : 南风
             * fl : <3级
             * type : 多云
             * notice : 阴晴之间，谨防紫外线侵扰
             */

            private String date;
            private String sunrise;
            private String high;
            private String low;
            private String sunset;
            private int aqi;
            private String ymd;
            private String week;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class ForecastBean {
            /**
             * date : 23
             * sunrise : 05:30
             * high : 高温 23.0℃
             * low : 低温 16.0℃
             * sunset : 18:52
             * aqi : 61
             * ymd : 2019-04-23
             * week : 星期二
             * fx : 南风
             * fl : <3级
             * type : 阴
             * notice : 不要被阴云遮挡住好心情
             */

            private String date;
            private String sunrise;
            private String high;
            private String low;
            private String sunset;
            private int aqi;
            private String ymd;
            private String week;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getYmd() {
                return ymd;
            }

            public void setYmd(String ymd) {
                this.ymd = ymd;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }
}
