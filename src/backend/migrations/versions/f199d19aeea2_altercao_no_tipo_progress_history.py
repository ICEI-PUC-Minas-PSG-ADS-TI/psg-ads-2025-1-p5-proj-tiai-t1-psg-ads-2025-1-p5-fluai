"""Altercao no tipo progress_history

Revision ID: f199d19aeea2
Revises: 569bb7db522b
Create Date: 2025-03-18 15:54:11.897743

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'f199d19aeea2'
down_revision: Union[str, None] = '569bb7db522b'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    op.alter_column('users', 'progress_history', type_=sa.Text)


def downgrade() -> None:
    op.alter_column('users', 'progress_history', type_=sa.String(100))
