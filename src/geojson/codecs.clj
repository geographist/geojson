(ns geojson.codecs
    "Codecs for reading / writing GeoJSON features"
    (:require [cheshire.core :refer :all]
              [clj-http.client :as client]
              [geojson.validate :as validate]))

(defn from-json-string
    "Parse GeoJSON object from string"
    [json-string]
    (let [json-hash-map (decode json-string true)]
        (if (validate/valid? json-hash-map)
            json-hash-map
            {:error "Invalid GeoJSON"})))

(defn to-json-string
    "Write valid GeoJSON object to string"
    [geojson-hash-map]
    (encode geojson-hash-map))

(defn from-url
    "Fetch and parse a GeoJSON body"
    [url]
    (let [body ((client/get url {:as :json}) :body)]
        (if (validate/valid? body)
            body
            {:error "Invalid GeoJSON"})))
