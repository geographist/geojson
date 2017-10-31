(ns geojson.test.codecs
    (:require [clojure.test :refer :all]
              [geojson.codecs :as codecs]))

(deftest test-from-json-string
    (is (= {:type "Point" :coordinates [0 0]} (codecs/from-json-string "{\"type\":\"Point\",\"coordinates\":[0,0]}"))))

(deftest test-to-json-string
    (is (= "{\"type\":\"Point\",\"coordinates\":[0,0]}" (codecs/to-json-string {:type "Point" :coordinates [0 0]}))))

;;;; TODO: test-from-url
