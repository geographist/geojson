(ns geojson.test.builders
    (:require [clojure.test :refer :all]
              [geojson.builders :as builders]
              [geojson.validate :as validate]))

(deftest test-coordinate
    (is (= (builders/coordinate [0 0]) [0 0]))
    (is (= (builders/coordinate '(0 0)) [0 0]))
    (is (= (builders/coordinate {:x 0 :y 0}) [0 0]))
    (is (= (builders/coordinate (hash-map :x 0 :y 0)) [0 0]))
    (is (nil? (builders/coordinate [0 98])))
    (is (nil? (builders/coordinate '(181 0))))
    (is (nil? (builders/coordinate {:x 0 :y 98.7})))
    (is (nil? (builders/coordinate (hash-map :x 181.2 :y 0)))))

(deftest test-point
    (let [null-island {:type "Point" :coordinates [0 0]}]
        (is (= (builders/point [0 0]) null-island))
        (is (= (builders/point '(0 0)) null-island))
        (is (= (builders/point {:x 0 :y 0}) null-island))
        (is (= (builders/point (hash-map :x 0 :y 0)) null-island)))
    (is (nil? (builders/point [0 98])))
    (is (nil? (builders/point '(181 0))))
    (is (nil? (builders/point {:x 0 :y 98.7})))
    (is (nil? (builders/point (hash-map :x 181.2 :y 0)))))

(deftest multi-point
    (let [test-points {:type "MultiPoint" :coordinates [[0 0] [1 1] [2 2]]}
          test-points-nil {:type "MultiPoint" :coordinates [[0 0] [1 1]]}]
        (is (= (builders/multi-point [[0 0] [1 1] [2 2]]) test-points))
        (is (= (builders/multi-point ['(0 0) {:x 1 :y 1} (hash-map :x 2 :y 2)]) test-points))
        (is (= (builders/multi-point '([0 0] [1 1] [2 2])) test-points))
        (is (= (builders/multi-point '((0 0) (1 1) (2 2))) test-points))
        (is (= (builders/multi-point [[0 0] [1 1] [2 98.3]]) test-points-nil))))
