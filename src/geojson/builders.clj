(ns geojson.builders
    "Basic builders for GeoJSON objects"
    (:require [clojure.walk :as walk]
              [geojson.validate :as validate]))

(defn- valid-lng?
    "Checks for valid longitude (x) value"
    [lng]
    (and (<= lng 180)
         (>= lng -180)))

(defn- valid-lat?
    "Checks for valid latitude (y) value"
    [lat]
    (and (<= lat 90)
         (>= lat -90)))

(defn- valid-coord?
    "Checks whether coordinates are in bounds"
    [coord]
    (and (valid-lng? (first coord))
         (valid-lat? (coord 1))))

(defmulti coordinate
    "Builds a valid GeoJSON coordinate"
    (fn [x] (class x)))

(defmethod coordinate
    clojure.lang.PersistentVector
    [coord]
    (if (valid-coord? coord)
        coord))

(defmethod coordinate
    clojure.lang.PersistentList
    [coord]
    (coordinate [(nth coord 0) (nth coord 1)]))

(defmethod coordinate
    clojure.lang.PersistentArrayMap
    [coord]
    (coordinate [(coord :x) (coord :y)]))

(defmethod coordinate
    clojure.lang.PersistentHashMap
    [coord]
    (coordinate [(coord :x) (coord :y)]))

(defmethod coordinate :default [coord] nil)

(defn point
    "Builds a GeoJSON Point from input"
    [coord]
    (let [point-coord (coordinate coord)]
        (if (nil? point-coord)
            nil
            {:type "Point"
             :coordinates point-coord})))

(defmulti mp-or-ls
    "Builds a coordinate vector for MultiPoint or LineString geometries"
    (fn [x] (class x)))

(defmethod mp-or-ls
    clojure.lang.PersistentVector
    [coords]
    (filter some? (map coordinate coords)))

(defmethod mp-or-ls
    clojure.lang.PersistentList
    [coords]
    (filter some? (map coordinate coords)))

(defn multi-point
    "Builds a GeoJSON MultiPoint from input."
    [coords]
    {:type "MultiPoint"
     :coordinates (vec (mp-or-ls coords))})

(defn linestring
    "Build a GeoJSON LineString from input."
    [coords]
    {:type "LineString"
     :coordinates (vec (mp-or-ls coords))})
