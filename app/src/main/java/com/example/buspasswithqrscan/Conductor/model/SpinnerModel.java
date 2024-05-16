package com.example.buspasswithqrscan.Conductor.model;

public class SpinnerModel{
    public class Journey {
        private int id;
        private String name;

        public Journey(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name; // This is what will be displayed in the spinner
        }
    }
}
