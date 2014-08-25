(ns z_values.main
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io])
  (:gen-class))
;;define 'sum' function
(defn sum [seq] (reduce (fn [a b] (+ a b)) seq))
;;raw data
(def data
  (with-open [in-file (io/reader "resources/dummyData.csv")]
    (doall
      (csv/read-csv in-file))))
;;z-value function
(defn z_values [seq]
  (let [mean (/ (sum seq) (count seq))
        squares (map #(* %1 %1) seq)
        variance (sum squares)
        sd (Math/sqrt variance)]
    (map #(/ (- % mean) sd) seq)))
;; value seqs
(def lengths (map #(let [[x _] %] (Double/parseDouble x)) (rest data) ))
(def widths (map #(let [[_ x] %] (Double/parseDouble x)) (rest data) ))
;;final z-values
(def width_z_values (z_values widths))
(def length_z_values (z_values lengths))

(defn write [seq]
  (with-open [out-file (io/writer "resources/out-file2.csv")]
    (csv/write-csv out-file
      seq)))

(defn -main [] (write (map vector length_z_values width_z_values)))