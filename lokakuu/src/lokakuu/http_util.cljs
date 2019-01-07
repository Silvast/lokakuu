(ns lokakuu.http-util
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<! >! take]]
            [reagent.core :as r]))


(defn get-blog-posts []
  (http/get "http://localhost:8000/wp-json/wp/v2/posts"))

(defn get-one-post [slug]
  (http/get (str "http://localhost:8000/wp-json/wp/v2/posts?slug=" slug)))
