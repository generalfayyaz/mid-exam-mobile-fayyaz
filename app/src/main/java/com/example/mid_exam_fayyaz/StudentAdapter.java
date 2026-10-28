package com.example.mid_exam_fayyaz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    List<StudentModel> students;
    Context context;
    DBHelper dbHelper;

    public StudentAdapter(List<StudentModel> students, Context context) {
        this.students = students;
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.student_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final StudentModel student = students.get(position);

        holder.textViewID.setText(String.valueOf(student.getId()));
        holder.editTextName.setText(student.getName());
        holder.editTextStudentId.setText(student.getStudentId());
        holder.checkBoxEnrolled.setChecked(student.isEnrolled());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName = holder.editTextName.getText().toString().trim();
                String strStudentId = holder.editTextStudentId.getText().toString().trim();
                boolean enrolled = holder.checkBoxEnrolled.isChecked();

                if (strName.isEmpty() || strStudentId.isEmpty()) {
                    Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean ok = dbHelper.updateStudent(
                        new StudentModel(student.getId(), strName, strStudentId, enrolled));

                if (ok) {
                    student.setName(strName);
                    student.setStudentId(strStudentId);
                    student.setEnrolled(enrolled);
                    Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Student ID already used", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                dbHelper.deleteStudent(student.getId());
                students.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, students.size());
                Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID;
        EditText editTextName, editTextStudentId;
        CheckBox checkBoxEnrolled;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewID);
            editTextName = itemView.findViewById(R.id.editTextName);
            editTextStudentId = itemView.findViewById(R.id.editTextStudentId);
            checkBoxEnrolled = itemView.findViewById(R.id.checkBoxEnrolled);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}